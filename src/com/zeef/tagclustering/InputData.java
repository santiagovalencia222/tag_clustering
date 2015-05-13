package com.zeef.tagclustering;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Triplet;

import com.zeef.tagclustering.linkmanager.LinkRetriever;
import com.zeef.tagclustering.model.Resource;
import com.zeef.tagclustering.model.Tag;
import com.zeef.tagclustering.model.User;

public class InputData {

	private Set<Tag> tags;
	private Set<User> users;
	private Set<Resource> resources;
	private Map<Triplet<Tag, Resource, User>, Boolean> tensor;

	public InputData() {
		tags = new HashSet<>();
		users = new HashSet<>();
		resources = new HashSet<>();
		tensor = new HashMap<>();
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Set<User> getUsers() {
		return users;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public Map<Triplet<Tag, Resource, User>, Boolean> getTensor() {
		return tensor;
	}

	public void addTripletToTensor(Tag tag, Resource resource, User user) {
		tensor.put(new Triplet<>(tag, resource, user), true);
	}

	public void populateTensor(Set<Triplet<List<Tag>, Resource, User>> resultSet) {
		for (Triplet<List<Tag>, Resource, User> rs : resultSet) {
			for (User user : users) {
				for (Resource resource : resources) {
					for (Tag tag : tags) {
						if (rs.getValue0().contains(tag) &&
								resource.equals(rs.getValue1()) &&
										user.equals(rs.getValue2())) {
							tensor.put(new Triplet<>(tag, resource, user), true);
						} else {
							tensor.put(new Triplet<>(tag, resource, user), false);
						}
					}
				}
			}
		}
		/*resultSet.parallelStream()
		.forEach(rs -> users.parallelStream()
				.forEach(user -> resources.parallelStream()
						.forEach(resource -> tags.parallelStream()
								.filter(tag -> (rs.getTags().contains(tag) &&
												resource.equals(rs.getResource()) &&
												user.equals(rs.getUser())))
								.forEach(tag -> addTripleToTensor(tag, user, resource)))));*/
	}

	public void generateInputData() {
		LinkRetriever retriever = new LinkRetriever();
		ResultSet resultSet = retriever.getTaggedLinks();
		Set<Triplet<List<Tag>, Resource, User>> rs = new HashSet<>();
		try {
			while (resultSet.next()) {
				List<Tag> tagsRS = new ArrayList<>();
				tagsRS.add(new Tag(resultSet.getString("tag_name")));
				tagsRS.add(new Tag(resultSet.getString("page_title")));
				tagsRS.add(new Tag(resultSet.getString("block_title")));
				rs.add(new Triplet<>(tagsRS,
						new Resource(resultSet.getString("target_url")),
						new User(resultSet.getString("full_name"))));

				tags.add(new Tag(resultSet.getString("tag_name")));
				tags.add(new Tag(resultSet.getString("page_title")));
				tags.add(new Tag(resultSet.getString("block_title")));
				users.add(new User(resultSet.getString("full_name")));
				resources.add(new Resource(resultSet.getString("target_url")));
			}
			populateTensor(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
