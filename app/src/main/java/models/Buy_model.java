package models;

public class Buy_model {
    String date,operator,Number,Amount,auth;
    Buy_model(){}

    public Buy_model(String date, String operator, String number, String amount, String auth) {
        this.date = date;
        this.operator = operator;
        Number = number;
        Amount = amount;
        this.auth = auth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
