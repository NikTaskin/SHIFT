package ru.focus.taskin;

public class MessageHandler {
    MessageHandler() {
    }

    public static void handle(ClientUI clientUI, Message msg) {
        if (msg.getNickname().equals("Server") && msg.getText().equals("Name already taken")) {
            clientUI.printMessage("Name is already taken, select another one.");
            clientUI.askForNickname();
            clientUI.connect();
        } else if (msg.getNickname().equals("Active users list")) {
            clientUI.updateUsersList(msg.getText());
        } else {
            clientUI.printMessage(msg.getTimestamp() + " " + msg.getNickname() + ": " + msg.getText());
        }
    }
}
