package hanwool.lotto.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Lotto extends RealmObject {
    @PrimaryKey
    private int drawNo;
    private int ball1;
    private int ball2;
    private int ball3;
    private int ball4;
    private int ball5;
    private int ball6;
    private int ballBonus;

    public Lotto() {
    }

    public Lotto(int drawNo, int ball1, int ball2, int ball3, int ball4, int ball5, int ball6, int ballBonus) {
        this.drawNo = drawNo;
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.ball3 = ball3;
        this.ball4 = ball4;
        this.ball5 = ball5;
        this.ball6 = ball6;
        this.ballBonus = ballBonus;
    }

    public int getDrawNo() {
        return drawNo;
    }

    public void setDrawNo(int drawNo) {
        this.drawNo = drawNo;
    }

    public int getBall1() {
        return ball1;
    }

    public void setBall1(int ball1) {
        this.ball1 = ball1;
    }

    public int getBall2() {
        return ball2;
    }

    public void setBall2(int ball2) {
        this.ball2 = ball2;
    }

    public int getBall3() {
        return ball3;
    }

    public void setBall3(int ball3) {
        this.ball3 = ball3;
    }

    public int getBall4() {
        return ball4;
    }

    public void setBall4(int ball4) {
        this.ball4 = ball4;
    }

    public int getBall5() {
        return ball5;
    }

    public void setBall5(int ball5) {
        this.ball5 = ball5;
    }

    public int getBall6() {
        return ball6;
    }

    public void setBall6(int ball6) {
        this.ball6 = ball6;
    }

    public int getBallBonus() {
        return ballBonus;
    }

    public void setBallBonus(int ballBonus) {
        this.ballBonus = ballBonus;
    }
}
