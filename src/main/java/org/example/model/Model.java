package org.example.model;

import org.example.dto.Card;
import org.example.dto.Terminal;
import org.example.dto.Transaction;

public class Model {
    private Transaction transaction;
    private Terminal terminal;
    private Card card;


    public Model(Transaction transaction, Terminal terminal, Card card) {
        this.transaction = transaction;
        this.terminal = terminal;
        this.card = card;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return card.getCardNumber() + " " + terminal.getAddress() + " " + transaction.getAmount() + " " +
                transaction.getCreatedDate() + " " + transaction.getTransactionType();
    }
}
