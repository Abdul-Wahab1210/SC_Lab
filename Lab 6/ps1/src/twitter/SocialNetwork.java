package twitter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets a list of tweets providing the evidence, not modified by this method.
     * @return a social network (as defined above) in which User A follows User B 
     *         if and only if there is evidence for it in the given list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        // Regex to capture @-mentions
        Pattern mentionPattern = Pattern.compile("@(\\w+)");

        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor().toLowerCase();
            Matcher matcher = mentionPattern.matcher(tweet.getText());

            // Ensure that the author is present in the graph
            followsGraph.putIfAbsent(author, new HashSet<>());

            // Collect all @-mentions in the tweet
            while (matcher.find()) {
                String mentionedUser = matcher.group(1).toLowerCase();

                // Avoid self-following and add mentioned user to the set of users the author follows
                if (!mentionedUser.equals(author)) {
                    followsGraph.get(author).add(mentionedUser);
                    // Ensure the mentioned user is also in the graph
                    followsGraph.putIfAbsent(mentionedUser, new HashSet<>());
                }
            }
        }

        return followsGraph;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        // Map to store the number of followers for each user
        Map<String, Integer> followerCount = new HashMap<>();

        // Count the number of followers for each user
        for (Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
            String user = entry.getKey();
            Set<String> follows = entry.getValue();

            // For each user this user follows, increase the follower count
            for (String followedUser : follows) {
                followerCount.put(followedUser, followerCount.getOrDefault(followedUser, 0) + 1);
            }
        }

        // Ensure all users are included in the follower count, even if they have no followers
        for (String user : followsGraph.keySet()) {
            followerCount.putIfAbsent(user, 0);
        }

        // Create a list of influencers sorted by follower count
        List<String> influencers = new ArrayList<>(followerCount.keySet());

        // Sort by follower count in descending order
        influencers.sort((a, b) -> followerCount.get(b).compareTo(followerCount.get(a)));

        // If the size of the list is one and the only user has zero followers, return an empty list
        if (influencers.size() == 1 && followerCount.get(influencers.get(0)) == 0) {
            return new ArrayList<>(); // return empty for a single user with no followers
        }

        return influencers;
    }


}
