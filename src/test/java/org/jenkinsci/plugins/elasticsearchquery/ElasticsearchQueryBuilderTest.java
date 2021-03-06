package org.jenkinsci.plugins.elasticsearchquery;

import static hudson.util.FormValidation.Kind.ERROR;
import static hudson.util.FormValidation.Kind.OK;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import org.jenkinsci.plugins.elasticsearchquery.ElasticsearchQueryBuilder.DescriptorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ElasticsearchQueryBuilder.DescriptorImpl.class)
public class ElasticsearchQueryBuilderTest {
	
	@Before
	public void init() {
		suppress(constructor(ElasticsearchQueryBuilder.DescriptorImpl.class));
	}
		
	@Test
	public void testDoCheckQuery() throws Exception {
		assertEquals(ERROR, new DescriptorImpl().doCheckQuery("").kind);
		assertEquals(OK, new DescriptorImpl().doCheckQuery("q").kind);
	}
	
	@Test
	public void testDoCheckIndexes() throws Exception {
		assertEquals(ERROR, new DescriptorImpl().doCheckIndexes(", ,").kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckIndexes(",").kind);
		assertEquals(OK, new DescriptorImpl().doCheckIndexes("q").kind);
		assertEquals(OK, new DescriptorImpl().doCheckIndexes("").kind);
	}
	
	@Test
	public void testDoCheckSince() throws Exception {
		assertEquals(ERROR, new DescriptorImpl().doCheckSince(null).kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckSince(0L).kind);
		assertEquals(OK, new DescriptorImpl().doCheckSince(1L).kind);
	}
	
	@Test
	public void testDoCheckThreshold() throws Exception {
		assertEquals(ERROR, new DescriptorImpl().doCheckThreshold(null).kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckThreshold("").kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckThreshold("   ").kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckThreshold(" 5 6 ").kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckThreshold("asdf").kind);
		assertEquals(OK, new DescriptorImpl().doCheckThreshold("  6 ").kind);
		assertEquals(OK, new DescriptorImpl().doCheckThreshold("1").kind);
		assertEquals(OK, new DescriptorImpl().doCheckThreshold("0").kind);
	}
	
	@Test
	public void testDoCheckQueryRequestTimeout() throws Exception {
		assertEquals(ERROR, new DescriptorImpl().doCheckQueryRequestTimeout(-1).kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckQueryRequestTimeout(0).kind);
		assertEquals(ERROR, new DescriptorImpl().doCheckQueryRequestTimeout(null).kind);
		assertEquals(OK, new DescriptorImpl().doCheckQueryRequestTimeout(10).kind);
	}
}
