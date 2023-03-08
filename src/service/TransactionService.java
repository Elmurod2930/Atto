package service;

import container.ComponentContainer;
import dto.Card;
import dto.Profile;
import dto.Terminal;
import dto.Transaction;
import enums.TransactionType;
import model.Model;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    public void createTransaction(Integer cardId, Integer terminalId, Double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setTerminalId(terminalId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());

        TransactionRepository transactionRepository = ComponentContainer.transactionRepository;
        transactionRepository.createTransaction(transaction);
    }

    public void transactionList() {
        List<Transaction> transactionList = ComponentContainer.transactionRepository.transactionList();
        transactionList.forEach(System.out::println);
    }

    public void todayTransactionList() {
        List<Transaction> transactionList = ComponentContainer.transactionRepository.todayTransactionList();
        transactionList.forEach(System.out::println);
    }

    public void transactionByDay(String date) {
        List<Transaction> transactionList = ComponentContainer.transactionRepository.transactionByDay(date);
        transactionList.forEach(System.out::println);
    }

    public void transactionBetweenDays(String fromDate, String toDate) {
        List<Transaction> transactionList = ComponentContainer.transactionRepository.transactionBetweenDays(fromDate, toDate);
        transactionList.forEach(System.out::println);
    }

    public void transactionByTerminal(Integer number) {
        if (ComponentContainer.terminalRepository.getTerminalById(number) == null) {
            System.out.println("not fount terminal!");
            return;
        }
        List<Transaction> transactionList = ComponentContainer.transactionRepository.transactionByTerminal(number);
        transactionList.forEach(System.out::println);
    }

    public void transactionByCard(String number) {
        Card card = ComponentContainer.cardRepository.getCardByNumber(number);
        if (card == null) {
            System.out.println("not found card");
            return;
        }
        List<Transaction> transactionList = ComponentContainer.transactionRepository.transactionByCard(card.getId());
        transactionList.forEach(System.out::println);
    }

    public void makePayment(String number, String code) {
        Card card = ComponentContainer.cardRepository.getCardByNumber(number);
        if (card == null) {
            System.out.println("not found card");
            return;
        }
        Terminal terminal = ComponentContainer.terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("not fount terminal");
        }
        ComponentContainer.transactionRepository.makePayment(card, terminal.getId());
    }

    public void transactionList(Profile profile) {
        List<Card> cardList = ComponentContainer.cardRepository.getCardByProfilePhone(profile.getPhone());
        for (Card card : cardList) {
            List<Model> transactionList = ComponentContainer.transactionRepository.transactionList(card);
            transactionList.forEach(System.out::println);
        }
    }
}
