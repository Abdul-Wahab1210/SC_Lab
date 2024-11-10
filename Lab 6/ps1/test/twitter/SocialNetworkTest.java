package twitter;

import static org.junit.Assert.*;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

public class SocialNetworkTest {

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        List<Tweet> tweets = new ArrayList<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("Expected empty graph when no tweets are provided", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<Tweet> tweets = Arrays.asList(
            new Tweet(1, "alice", "Hello world", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("Expected 'alice' to exist in the graph but have no followers", 
                   followsGraph.containsKey("alice") && followsGraph.get("alice").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphWithMentions() {
        List<Tweet> tweets = Arrays.asList(
            new Tweet(1, "alice", "Hello @bob", Instant.now()),
            new Tweet(2, "charlie", "Hey @alice and @bob!", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("Expected 'alice' to follow 'bob'", followsGraph.get("alice").contains("bob"));
        assertTrue("Expected 'charlie' to follow 'alice'", followsGraph.get("charlie").contains("alice"));
        assertTrue("Expected 'charlie' to follow 'bob'", followsGraph.get("charlie").contains("bob"));
    }

    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        List<Tweet> tweets = Arrays.asList(
            new Tweet(1, "alice", "Hi @bob and @charlie", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("Expected 'alice' to follow 'bob'", followsGraph.get("alice").contains("bob"));
        assertTrue("Expected 'alice' to follow 'charlie'", followsGraph.get("alice").contains("charlie"));
    }

    @Test
    public void testGuessFollowsGraphMultipleTweetsSameAuthor() {
        List<Tweet> tweets = Arrays.asList(
            new Tweet(1, "alice", "Hi @bob", Instant.now()),
            new Tweet(2, "alice", "Hey @charlie", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("Expected 'alice' to follow 'bob'", followsGraph.get("alice").contains("bob"));
        assertTrue("Expected 'alice' to follow 'charlie'", followsGraph.get("alice").contains("charlie"));
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("Expected empty list of influencers", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingle() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("bob", new HashSet<>()); // Bob has no followers
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("Expected empty list of influencers when there's only one user with no followers", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        // Setup follows graph
        followsGraph.put("alice", new HashSet<>(Arrays.asList("bob"))); // alice follows bob
        followsGraph.put("bob", new HashSet<>()); // bob has no followers

        // Get influencers
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        // Expected order: bob (0 followers), alice (1 follower)
        assertEquals("Expected 'bob' to be the top influencer", "bob", influencers.get(0));
    }


    @Test
    public void testInfluencersMultiple() {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        // Setup follows graph
        followsGraph.put("alice", new HashSet<>(Arrays.asList("bob", "charlie"))); // alice follows bob and charlie
        followsGraph.put("bob", new HashSet<>(Arrays.asList("charlie"))); // bob follows charlie
        followsGraph.put("charlie", new HashSet<>()); // charlie has no followers

        // Get influencers
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        // Expected order: charlie (2 followers), bob (1 follower), alice (0 followers)
        assertEquals("Expected 'charlie' to be the top influencer", "charlie", influencers.get(0));
        assertEquals("Expected 'bob' to be the second influencer", "bob", influencers.get(1));
        assertEquals("Expected 'alice' to be the third influencer", "alice", influencers.get(2));
    }


    @Test
    public void testInfluencersEqualCount() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("alice", new HashSet<>(Arrays.asList("bob"))); // Alice follows Bob
        followsGraph.put("bob", new HashSet<>(Arrays.asList("alice"))); // Bob follows Alice

        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("Expected both 'alice' and 'bob' to be influencers", 
                   influencers.contains("alice") && influencers.contains("bob"));
    }
}
