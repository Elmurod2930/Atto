package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.example.enums.TransactionType;
import org.example.model.Model;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository;
    private CardRepository cardRepository;
    private TerminalRepository terminalRepository;

    public void setTerminalRepository(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(Integer cardId, Integer terminalId, Double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setTerminalId(terminalId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());

        transactionRepository.createTransaction(transaction);
    }

    public void transactionList() {
        List<Transaction> transactionList = transactionRepository.transactionList();
        transactionList.forEach(System.out::println);
    }

    public void todayTransactionList() {
        List<Transaction> transactionList = transactionRepository.todayTransactionList();
        transactionList.forEach(System.out::println);
    }

    public void transactionByDay(String date) {
        List<Transaction> transactionList = transactionRepository.transactionByDay(date);
        transactionList.forEach(System.out::println);
    }

    public void transactionBetweenDays(String fromDate, String toDate) {
        List<Transaction> transactionList = transactionRepository.transactionBetweenDays(fromDate, toDate);
        transactionList.forEach(System.out::println);
    }

    public void transactionByTerminal(Integer number) {
        if (terminalRepository.getTerminalById(number) == null) {
            System.out.println("not fount terminal!");
            return;
        }
        List<Transaction> transactionList = transactionRepository.transactionByTerminal(number);
        transactionList.forEach(System.out::println);
    }

    public void transactionByCard(String number) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null) {
            System.out.println("not found card");
            return;
        }
        List<Transaction> transactionList = transactionRepository.transactionByCard(card.getId());
        transactionList.forEach(System.out::println);
    }

    public void makePayment(String number, String code) {
        Card card = cardRepository.getCardByNumber(number);
        if (card == null) {
            System.out.println("not found card");
            return;
        }
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("not fount terminal");
        }
        transactionRepository.makePayment(card, terminal.getId());
    }

//    public void transactionList(Profile profile) {
//        List<Card> cardList = cardRepository.getCardByProfilePhone(profile.getPhone());
//        for (Card card : cardList) {
//            List<Model> transactionList = transactionRepository.transactionList(card);
//            transactionList.forEach(System.out::println);
//        }
//    }
}
