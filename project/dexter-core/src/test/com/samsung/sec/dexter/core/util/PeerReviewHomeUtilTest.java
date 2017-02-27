package com.samsung.sec.dexter.core.util;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.samsung.sec.dexter.core.config.PeerReviewHome;
import com.samsung.sec.dexter.core.config.PeerReviewHomeJson;

public class PeerReviewHomeUtilTest {
	PeerReviewHomeUtil util;

	@Before
	public void setUp() throws Exception {
		util = new PeerReviewHomeUtil(new Gson());
	}

	@Test
	public void loadJson_GivenAReader_loadPeerRevieHomeJson() {
		// given
		Reader reader = createTestJsonStringReader();
		PeerReviewHomeJson testJson = createTestJsonString();
		
		// when
		PeerReviewHomeJson homeJson = util.loadJson(reader);
		
		// then
		assertEquals(testJson, homeJson);
	}
	
	private PeerReviewHomeJson createTestJsonString() {
		DexterServerConfig config = new DexterServerConfig("testId", "testPw", "127.0.0.1", 8080);
		PeerReviewHome firstHome = new PeerReviewHome(null, "testProject", "/test", true);
		PeerReviewHome secondHome = new PeerReviewHome(null, "testProject2", "/test2", true);
		List<PeerReviewHome> homeList = new ArrayList<PeerReviewHome>();
		homeList.add(firstHome);
		homeList.add(secondHome);
		
		return new PeerReviewHomeJson(config, homeList);
	}

	private Reader createTestJsonStringReader() {
		String jsonString =
				"{" +
				"  \"server\" : {"	+
				"  \"ip\" : \"127.0.0.1\"," +
				"  \"port\" : 8080," +
				"  \"id\" : \"testId\"," +
				"  \"pw\" : \"testPw\"" +
				"  }," +
				"  \"home\" : [{" +
				"    \"projectName\" : \"testProject\"," +
				"    \"sourceDir\": \"/test\"," +
				"    \"active\" : true" +
				"  }, {" +
				"    \"projectName\" : \"testProject2\"," +
				"    \"sourceDir\": \"/test2\"," +
				"    \"active\" : false" +
				"  }]" +
				"}";
		
	    return new StringReader(jsonString);
	}

}