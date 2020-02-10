package protzek.sebastian.mastermindlogicgame;

public class SingleTurn {

    private int firstBall;
    private int secondBall;
    private int thirdBall;
    private int fourthBall;
    private int firstScorePin;
    private int secondScorePin;
    private int thirdScorePin;
    private int fourthScorePin;

    SingleTurn(int firstBall, int secondBall, int thirdBall, int fourthBall,
               int firstScorePin, int secondScorePin, int thirdScorePin, int fourthScorePin) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.thirdBall = thirdBall;
        this.fourthBall = fourthBall;
        this.firstScorePin = firstScorePin;
        this.secondScorePin = secondScorePin;
        this.thirdScorePin = thirdScorePin;
        this.fourthScorePin = fourthScorePin;
    }

    int getFirstBall() {
        return firstBall;
    }

    public void setFirstBall(int firstBall) {
        this.firstBall = firstBall;
    }

    int getSecondBall() {
        return secondBall;
    }

    public void setSecondBall(int secondBall) {
        this.secondBall = secondBall;
    }

    int getThirdBall() {
        return thirdBall;
    }

    public void setThirdBall(int thirdBall) {
        this.thirdBall = thirdBall;
    }

    int getFourthBall() {
        return fourthBall;
    }

    public void setFourthBall(int fourthBall) {
        this.fourthBall = fourthBall;
    }

    int getFirstScorePin() {
        return firstScorePin;
    }

    void setFirstScorePin(int firstScorePin) {
        this.firstScorePin = firstScorePin;
    }

    int getSecondScorePin() {
        return secondScorePin;
    }

    void setSecondScorePin(int secondScorePin) {
        this.secondScorePin = secondScorePin;
    }

    int getThirdScorePin() {
        return thirdScorePin;
    }

    void setThirdScorePin(int thirdScorePin) {
        this.thirdScorePin = thirdScorePin;
    }

    int getFourthScorePin() {
        return fourthScorePin;
    }

    void setFourthScorePin(int fourthScorePin) {
        this.fourthScorePin = fourthScorePin;
    }
}
