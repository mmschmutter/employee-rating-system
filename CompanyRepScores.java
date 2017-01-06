import java.util.Arrays;
/**
 * A system which allows a manager to track a company representative's customer ratings and average customer rating.
 * An alert is returned when a representative's score drops below 2.5.
 * 
 * @author Mordechai Schmutter 
 * @version 1.0
 */
public class CompanyRepScores
{
    private int repQuantity;
    private int numberOfPossibleScores;
    private int[][] scores;
    private int[][] lastTwenty;
    private int[] indexCount;
    private double[] averages;
    private boolean[] below;

    public CompanyRepScores(int repQuantity, int scoreQuantity)
    {
        this.repQuantity = repQuantity;
        this.numberOfPossibleScores = scoreQuantity;
        this.scores = new int[this.repQuantity][this.numberOfPossibleScores];
        this.lastTwenty = new int[this.repQuantity][20];
        this.indexCount = new int[this.repQuantity];
        this.averages = new double[this.repQuantity];
        this.below = new boolean[this.repQuantity];
        //initialize all score counts to zero
        for(int i = 0; i < this.scores.length; i++)
        {
            Arrays.fill(this.scores[i],0);
        }
        Arrays.fill(this.below,true);
    }

    /**
     * @param repID the representative who received this score.
     * @param score the score received
     */
    public void addNewScore(int repID, int score)
    {
        this.scores[repID][score-1] += 1;

        if(this.indexCount[repID] == 20){
            this.lastTwenty[repID][0] = score;
            this.indexCount[repID] = 1;
        }
        else{
            this.lastTwenty[repID][this.indexCount[repID]] = score;
            this.indexCount[repID]++;
        }

        updateAverage(repID);
    }

    /**
     * @param repID the id of the rep
     */
    private void updateAverage(int repID)
    {
        double sum = 0;
        double average = 0;
        for( int i : lastTwenty[repID]) {
            sum += i;
        }
        average = sum/20;
        if(below[repID] == false && average < 2.5)
        {
            System.out.println("Rep " + repID + "'s running average has dropped to " + average + ".");
            below[repID] = true;
        }
        if(average > 2.5){
            below[repID] = false;
        }
        averages[repID] = average;
    }

    /**
     * @param repID the id of the rep
     * @return an array with the current score totals for the rep
     */
    public int[] getCumulativeScoreForRep(int repID)
    {
        return Arrays.copyOf(this.scores[repID],this.scores[repID].length);
    }

    /**
     * @param repID the id of the rep
     * @return average of last 20 scores for the rep
     */
    public double getAverageScoreForRep(int repID)
    {
        return this.averages[repID];
    }

    /**
     * @param repID the id of the rep
     */
    public ArrayAndDouble getAverageandScores(int repID)
    {
        ArrayAndDouble answer = new ArrayAndDouble();
        answer.arr = getCumulativeScoreForRep(repID);
        answer.num = getAverageScoreForRep(repID);
        return answer;
    }

    /**
     * @param repID the id of the rep
     */
    public void resetRep(int repID)
    {
        Arrays.fill(this.scores[repID],0);
        averages[repID] = 0;
    }
    public void resetAllReps()
    {
        for(int i = 0; i < this.scores.length; i++)
        {
            Arrays.fill(this.scores[i],0);
        }
        for(int i = 0; i < this.averages.length; i++)
        {
            this.averages[i] = 0;
        }
    }
}
