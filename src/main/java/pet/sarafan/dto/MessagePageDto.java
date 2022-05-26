package pet.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonView;
import pet.sarafan.domain.Message;
import pet.sarafan.domain.Views;

import java.util.List;

@JsonView({Views.FullMessage.class})
public class MessagePageDto {

    private List<Message> messages;
    private int currentPage;
    private int totalPage;

    public MessagePageDto(List<Message> messages, int currentPage, int totalPage) {
        this.messages = messages;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


}
