public class KnapsackDP {
    //more time to do
    //whole optimization 
    private int num;
    private int targetWeight;
    private int[] value;
    private int[] weight;
    private int[][] x;
    private int[][] totalValue;
    private int[][] totalWeight;


    public KnapsackDP(String s) {
        String[] parts = s.split(" ");
        this.num = Integer.parseInt(parts[0]);
        this.targetWeight = Integer.parseInt(parts[1]);
        this.value = new int[num];
        this.weight = new int[num];
        this.x = new int[num + 1][targetWeight + 1];
        this.totalValue = new int[num + 1][targetWeight + 1];
        this.totalWeight = new int[num + 1][targetWeight + 1];
        for (int i = 0; i < num; i++) {
            this.value[i] = Integer.parseInt(parts[2 + 2 * i]);
            this.weight[i] = Integer.parseInt(parts[3 + 2 * i]);
        }
    }

    private int solveRecursively(int i, int w) {
        if (i == 0 || w == 0) return 0;
        if (totalValue[i][w] != 0) return totalValue[i][w];

        if (weight[i - 1] <= w) {
            //if i <= w, put or not put, to get optimizied 
            int valueIfIncluded = value[i - 1] + solveRecursively(i - 1, w - weight[i - 1]);
            int valueIfExcluded = solveRecursively(i - 1, w);

            if (valueIfIncluded > valueIfExcluded) {
                totalValue[i][w] = valueIfIncluded;
                x[i][w] = 1; // Item i is included
                
            } else {
                totalValue[i][w] = valueIfExcluded;
                // default value is 0
            }
        } else {
            //i>w, not put 
            totalValue[i][w] = solveRecursively(i - 1, w);
            // it exceeds the weight
        }
        return totalValue[i][w];
    }

    public static String recMemo(String s) {
        //memoization
        KnapsackDP knapsack = new KnapsackDP(s);
        knapsack.solveRecursively(knapsack.num, knapsack.targetWeight);

        
        StringBuilder solution = new StringBuilder();
        int w = knapsack.targetWeight;
        for (int i = knapsack.num; i > 0; i--) {
            if (knapsack.x[i][w] == 1) {
                solution.insert(0, "1, ");
                w -= knapsack.weight[i - 1];
            } else {
                solution.insert(0, "0, ");
            }
        }

        
        if (solution.length() > 0) solution.setLength(solution.length() - 2);
        solution.append(String.format(", total value = %d, total weight = %d", knapsack.totalValue[knapsack.num][knapsack.targetWeight], knapsack.targetWeight - w));

        return solution.toString();
    }

    public String reconstructSolution() {
        StringBuilder solution = new StringBuilder();
        int w = targetWeight;
        for (int i = num; i > 0; i--) {
            if (x[i][w] == 1) {
                solution.insert(0, "1, ");
                w -= weight[i - 1];
            } else {
                solution.insert(0, "0, ");
            }
        }

       
        if (solution.length() >= 2) solution.setLength(solution.length() - 2);

        
        int totalWeightUsed = targetWeight - w;
        int totalValueAchieved = totalValue[num][targetWeight];
        
        solution.append(String.format(", total value = %d, total weight = %d", totalValueAchieved, totalWeightUsed));
        return solution.toString();
    }

    public static String nonRec(String s) {
        KnapsackDP knapsack = new KnapsackDP(s);
        knapsack.solveNonRecursively();
        return knapsack.reconstructSolution();
    }

    public void solveNonRecursively() {
        //two loops to traverse items and weight,
        //calculate previous value to calculate current value
        for (int i = 0; i <= num; i++) {
            for (int w = 0; w <= targetWeight; w++) {
                if (i == 0 || w == 0) {
                    totalValue[i][w] = 0;
                    totalWeight[i][w] = 0;
                } else if (weight[i - 1] <= w) {
                    //if current weight < current size, put or not
                    int valueIfIncluded = value[i - 1] + totalValue[i - 1][w - weight[i - 1]];
                    int valueIfExcluded = totalValue[i - 1][w];
                    if (valueIfIncluded > valueIfExcluded) {
                        totalValue[i][w] = valueIfIncluded;
                        totalWeight[i][w] = weight[i - 1] + totalWeight[i - 1][w - weight[i - 1]];
                        x[i][w] = 1;
                    } else {
                        //not put
                        totalValue[i][w] = valueIfExcluded;
                        totalWeight[i][w] = totalWeight[i - 1][w];
                    }
                } else {
                    
                    totalValue[i][w] = totalValue[i - 1][w];
                    totalWeight[i][w] = totalWeight[i - 1][w];
                }
            }
        }
    }
}