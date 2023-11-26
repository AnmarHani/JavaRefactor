import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * SensorDataProcessor class processes sensor data and writes the results to a file.
 */
public class SensorDataProcessor {
    // Sensor data and limits.
    public double[][][] data;
    public double[][] limit;

    /**
     * Constructor for SensorDataProcessor.
     *
     * @param data  3D array of sensor data
     * @param limit 2D array of limits
     */
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    /**
     * Calculates the average of an array of doubles.
     *
     * @param array Array of doubles
     * @return Average of the array
     */
    private double average(double[] array) {
        int i = 0;
        double val = 0;
        for (i = 0; i < array.length; i++) {
            val += array[i];
        }
        return val / array.length;
    }

    /**
     * Processes the sensor data and writes the results to a file.
     *
     * @param d Scaling factor for data processing
     */
    public void calculate(double d) {
        int i, j, k;
        double[][][] data2 = new double[data.length][data[0].length][data[0][0].length];

        try (BufferedWriter out = new BufferedWriter(new FileWriter("RacingStatsData.txt"))) {
            for (i = 0; i < data.length; i++) {
                for (j = 0; j < data[0].length; j++) {
                    for (k = 0; k < data[0][0].length; k++) {
                        data2[i][j][k] = data[i][j][k] / d - Math.pow(limit[i][j], 2.0);              
      if (average(data2[i][j]) > 10 && average(data2[i][j]) < 50) {
                        break;
                    } else if (Math.max(data[i][j][k], data2[i][j][k]) > data[i][j][k]) {
                        break;
                    } else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(data2[i][j][k]), 3)
                            && average(data[i][j]) < data2[i][j][k] && (i + 1) * (j + 1) > 0) {
                        data2[i][j][k] *= 2;
                    }
                }
            }
        }

        for (i = 0; i < data2.length; i++) {
            for (j = 0; j < data2[0].length; j++) {
                out.write(Arrays.toString(data2[i][j]) + "\n");
            }
        }
    } catch (IOException e) {
        System.out.println("Error= " + e);
    }
}
}
