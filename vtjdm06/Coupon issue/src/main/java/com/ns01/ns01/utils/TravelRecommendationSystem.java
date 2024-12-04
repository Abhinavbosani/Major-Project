package com.ns01.ns01.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ns01.ns01.model.RecRating;
import com.ns01.ns01.model.TravelRating;

public class TravelRecommendationSystem {

    private Map<String, Map<String, Double>> userPreferences = new HashMap<>();
    private Map<String, Map<String, Double>> destinationSimilarity = new HashMap<>();

    public void addUserPreference(String userId, String destinationId, double rating) {
        if (!userPreferences.containsKey(userId)) {
            userPreferences.put(userId, new HashMap<>());
        }
        userPreferences.get(userId).put(destinationId, rating);
    }

    public void addDestinationSimilarity(String destinationId1, String destinationId2, double similarity) {
        if (!destinationSimilarity.containsKey(destinationId1)) {
            destinationSimilarity.put(destinationId1, new HashMap<>());
        }
        destinationSimilarity.get(destinationId1).put(destinationId2, similarity);
        
    }

    public List<RecRating> getRecommendations(String userId) {
        List<RecRating> recommendations = new ArrayList<>();

        if (!userPreferences.containsKey(userId)) {
            return recommendations;
        }

        Map<String, Double> userRatings = userPreferences.get(userId);

        for (String destinationId : userRatings.keySet()) {
            double rating = userRatings.get(destinationId);

            for (String similarDestinationId : destinationSimilarity.get(destinationId).keySet()) {
                double similarity = destinationSimilarity.get(destinationId).get(similarDestinationId);
                double predictedRating = rating * similarity;
                      System.out.println("predict rating"+predictedRating);
                if (!userRatings.containsKey(similarDestinationId)) {
                    RecRating rr=new RecRating();
                    rr.setLocation(similarDestinationId);rr.setRating(predictedRating);
                    recommendations.add(rr);
                } else if (predictedRating > userRatings.get(similarDestinationId)) {
                   RecRating rr=new RecRating();
                    rr.setLocation(similarDestinationId);rr.setRating(predictedRating);
                    recommendations.add(rr);
                }
            }
        }

        return recommendations;
    }
}

