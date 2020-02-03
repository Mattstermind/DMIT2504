package ca.nait.mhorvath.chitchat;

public class Message
{
    private String messageSender = "";
    private String messageDate = "";
    private String messageContent = "";

    public Message(String messageSender, String messageDate, String messageContent)
    {
        this.messageSender = messageSender;
        this.messageDate = messageDate;
        this.messageContent = messageContent;
    }

    public Message()
    {

    }
    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
