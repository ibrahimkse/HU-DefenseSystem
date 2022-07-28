import java.util.ArrayList;

/**
 * This class accomplishes Mission Nuke'm
 */
public class DefenseAgainstEnemyTroops {
    private ArrayList<Integer> numberOfEnemiesArrivingPerHour;

    public DefenseAgainstEnemyTroops(ArrayList<Integer> numberOfEnemiesArrivingPerHour){
        this.numberOfEnemiesArrivingPerHour = numberOfEnemiesArrivingPerHour;
    }

    public ArrayList<Integer> getNumberOfEnemiesArrivingPerHour() {
        return numberOfEnemiesArrivingPerHour;
    }

    private int getRechargedWeaponPower(int hoursCharging){
        return hoursCharging*hoursCharging;
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalEnemyDefenseSolution
     */
    public OptimalEnemyDefenseSolution getOptimalDefenseSolutionDP(){
        ArrayList<Integer> sol = new ArrayList<Integer>();
        ArrayList<Integer> empty = new ArrayList<Integer>();
        sol.add(0,0);
        ArrayList<Integer>[] hours = new ArrayList[numberOfEnemiesArrivingPerHour.size()];
        hours[0] = empty;

        for (int j = 1; j <= numberOfEnemiesArrivingPerHour.size(); j++){
            if (j == numberOfEnemiesArrivingPerHour.size()){
                for (int k = 0; k < sol.size(); k++){
                    sol.set(k, sol.get(k) + Math.min(numberOfEnemiesArrivingPerHour.get(j-1), getRechargedWeaponPower(j-k)));
                    hours[k].add(j);
                }
            }
            else {
                int max = 0;
                int maxI = 0;
                for (int i = 0; i < j; i++){
                    int temp = sol.get(i) + Math.min(numberOfEnemiesArrivingPerHour.get(j-1), getRechargedWeaponPower(j-i));
                    if (temp > max){
                        max = temp;
                        maxI = i;
                    }
                }
                hours[j] = new ArrayList<>();
                for (int i: hours[maxI]){
                    hours[j].add(i);
                }
                hours[j].add(j);
                sol.add(j, max);
            }
        }
        int killMax = 0;
        int killMaxI = 0;
        for (int i = 0; i < sol.size(); i++){
            if (sol.get(i) > killMax){
                killMax = sol.get(i);
                killMaxI = i;
            }
        }

        OptimalEnemyDefenseSolution solution = new OptimalEnemyDefenseSolution(killMax, hours[killMaxI]);
        // TODO: YOUR CODE HERE
        return solution;
    }
}
