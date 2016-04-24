package ohtu;

public class TennisGame {

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private String playerOneName;
    private String playerTwoName;
    private int gamePoints = 4;
    private String[] calls = new String[4];

    public TennisGame(String player1Name, String player2Name) {
        this.playerOneName = player1Name;
        this.playerTwoName = player2Name;
        this.calls[0] = "Love";
        this.calls[1] = "Fifteen";
        this.calls[2] = "Thirty";
        this.calls[3] = "Forty";
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(this.playerOneName)) {
            playerOneScore += 1;
        } else {
            playerTwoScore += 1;
        }
    }

    public String getScore() {
        String score = "";
        if (playerOneScore >= gamePoints || playerTwoScore >= gamePoints) {
            if (playerOneScore == playerTwoScore) {
                return "Deuce";
            }
            score = advantageOrWin();
        } else {
            score = notGamePoint();
        }
        return score;
    }

    private String notGamePoint() {
        String score = calls[playerOneScore] + "-";
        if (playerTwoScore == playerOneScore) {
            score += "All";
        } else {
            score += calls[playerTwoScore];
        }
        return score;
    }

    private String advantageOrWin() {
        String score;
        int minusResult = playerOneScore - playerTwoScore;
        String inTheLead;
        if (minusResult > 0) {
            inTheLead = playerOneName;
        } else {
            inTheLead = playerTwoName;
        }
        if (Math.abs(minusResult) > 1) {
            score = "Win for "+inTheLead;
        } else {
            score = "Advantage "+inTheLead;
        }
        return score;
    }
}
