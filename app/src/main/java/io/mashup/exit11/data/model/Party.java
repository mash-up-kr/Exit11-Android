package io.mashup.exit11.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */
public class Party {

    @SerializedName("PartyId")
    private String id;

    @SerializedName("FoodId")
    private String foodId;

    @SerializedName("Description")
    private String desc;

    @SerializedName("Coordinate")
    private Coordinate coordinate;

    @SerializedName("PartyLeader")
    private int partyLeaderId;

    @SerializedName("TotalPeople")
    private int totalPeople;

    @SerializedName("MealTime")
    private String mealTime;

    @SerializedName("HashTag")
    private List<String> hashTags;

    @SerializedName("Status")
    private int status;

    public String getId() {
        return id;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getDesc() {
        return desc;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getPartyLeaderId() {
        return partyLeaderId;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public String getMealTime() {
        return mealTime;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public int getStatus() {
        return status;
    }
}
