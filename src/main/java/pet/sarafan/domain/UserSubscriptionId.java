package pet.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserSubscriptionId implements Serializable {
    @JsonView(Views.Id.class)
    private String channelId;

    @JsonView(Views.Id.class)
    private String SubscriberId;

    public UserSubscriptionId(String channelId, String subscriberId) {
        this.channelId = channelId;
        SubscriberId = subscriberId;
    }
    public UserSubscriptionId(){

    }
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSubscriberId() {
        return SubscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        SubscriberId = subscriberId;
    }
}
