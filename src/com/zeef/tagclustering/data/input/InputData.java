package com.zeef.tagclustering.data.input;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import com.zeef.tagclustering.data.linkmanager.LinkRetriever;
import com.zeef.tagclustering.model.Resource;
import com.zeef.tagclustering.model.Tag;
import com.zeef.tagclustering.model.User;

public class InputData {

	private Set<Tag> tags;
	private Set<User> users;
	private Set<Resource> resources;
	private Map<Triplet<Tag, Resource, User>, Boolean> _3Dtensor;
	private Map<Pair<Tag, Resource>, Boolean> _2Dtensor;

	public InputData() {
		tags = new HashSet<>();
		users = new HashSet<>();
		resources = new HashSet<>();
		_3Dtensor = new HashMap<>();
		_2Dtensor = new HashMap<>();
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

	public Map<Triplet<Tag, Resource, User>, Boolean> get3DTensor() {
		return _3Dtensor;
	}

	public Map<Pair<Tag, Resource>, Boolean> get2DTensor() {
		return _2Dtensor;
	}

	public void addTripletTo3DTensor(Tag tag, Resource resource, User user) {
		_3Dtensor.put(new Triplet<>(tag, resource, user), true);
	}

	public void addPairTo2DTensor(Tag tag, Resource resource) {
		_2Dtensor.put(new Pair<>(tag, resource), true);
	}

	public void populate3DTensor(Set<Triplet<List<Tag>, Resource, User>> resultSet) {
		for (Triplet<List<Tag>, Resource, User> rs : resultSet) {
			for (User user : users) {
				for (Resource resource : resources) {
					for (Tag tag : tags) {
						if (rs.getValue0().contains(tag) &&
								resource.equals(rs.getValue1()) &&
										user.equals(rs.getValue2())) {
							_3Dtensor.put(new Triplet<>(tag, resource, user), true);
						} else {
							_3Dtensor.put(new Triplet<>(tag, resource, user), false);
						}
					}
				}
			}
		}
	}

	public void populate2DTensor(Set<Triplet<List<Tag>, Resource, User>> resultSet) {
		for (Triplet<List<Tag>, Resource, User> rs : resultSet) {
			for (Resource resource : resources) {
				for (Tag tag : tags) {
					if (rs.getValue0().contains(tag) &&
							resource.equals(rs.getValue1())) {
						_2Dtensor.put(new Pair<>(tag, resource), true);
					} else {
						_2Dtensor.put(new Pair<>(tag, resource), false);
					}
				}
			}
		}
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
			populate3DTensor(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
