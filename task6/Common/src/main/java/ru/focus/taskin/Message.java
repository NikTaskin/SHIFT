package ru.focus.taskin;

public class Message {
    private String nickname;
    private String text;
    private String timestamp;

    public Message() {
    }

    public Message(String nickname, String text, String timestamp) {
        this.nickname = nickname;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
