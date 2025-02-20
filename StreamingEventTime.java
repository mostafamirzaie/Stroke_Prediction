import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StreamingEventTime {
    public static void main(String[] args) {

        try {

            /****************************************************************************
             *                 Setup Flink environment.
             ****************************************************************************/

            // Set up the streaming execution environment
            final StreamExecutionEnvironment streamEnv
                    = StreamExecutionEnvironment.getExecutionEnvironment();
            //streamEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
            streamEnv.setParallelism(1);

            /****************************************************************************
             *                  Read CSV File Stream into a DataStream.
             ****************************************************************************/
            ///////Read input1 Data
            DataStream<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> input1 = streamEnv.socketTextStream("localhost", 9090)
                    .map(new MapFunction<String, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>>() {
                        public Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> map(String value) throws ParseException {
                            String[] tcc = value.split(",");
                            return new Tuple23<>(new Double(tcc[0]), new Double(tcc[1]), new Double(tcc[2]), new Double(tcc[3]), new Double(tcc[4]), new Double(tcc[5]), new Double(tcc[6]), new Double(tcc[7]), new Double(tcc[8]), new Double(tcc[9]), new Double(tcc[10]), new Double(tcc[11]), new Double(tcc[12]), new Double(tcc[13]), new Double(tcc[14]), new Double(tcc[15]), new Double(tcc[16]), new Double(tcc[17]), new Double(tcc[18]), new Double(tcc[19]), new Double(tcc[20]), new Double(tcc[21]), new Double(tcc[22]));
                        }
                    });

            ///////Read input2 Data
            DataStream<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> input2 = streamEnv.socketTextStream("localhost", 9090)
                    .map(new MapFunction<String, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>>() {
                        public Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> map(String value) throws ParseException {
                            String[] tcc = value.split(",");
                            return new Tuple23<>(new Double(tcc[0]), new Double(tcc[1]), new Double(tcc[2]), new Double(tcc[3]), new Double(tcc[4]), new Double(tcc[5]), new Double(tcc[6]), new Double(tcc[7]), new Double(tcc[8]), new Double(tcc[9]), new Double(tcc[10]), new Double(tcc[11]), new Double(tcc[12]), new Double(tcc[13]), new Double(tcc[14]), new Double(tcc[15]), new Double(tcc[16]), new Double(tcc[17]), new Double(tcc[18]), new Double(tcc[19]), new Double(tcc[20]), new Double(tcc[21]), new Double(tcc[22]));
                        }
                    });

            ///////Read input3 Data
            DataStream<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> input3 = streamEnv.socketTextStream("localhost", 9090)
                    .map(new MapFunction<String, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>>() {
                        public Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> map(String value) throws ParseException {
                            String[] tcc = value.split(",");
                            return new Tuple25<>(new Double(tcc[0]), new Double(tcc[1]), new Double(tcc[2]), new Double(tcc[3]), new Double(tcc[4]), new Double(tcc[5]), new Double(tcc[6]), new Double(tcc[7]), new Double(tcc[8]), new Double(tcc[9]), new Double(tcc[10]), new Double(tcc[11]), new Double(tcc[12]), new Double(tcc[13]), new Double(tcc[14]), new Double(tcc[15]), new Double(tcc[16]), new Double(tcc[17]), new Double(tcc[18]), new Double(tcc[19]), new Double(tcc[20]), new Double(tcc[21]), new Double(tcc[22]));
                        }
                    });

            ///////////////////////Join
            DataStream<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> inputStream = input1
                    .join(input2)
                    .where(new KeySelector<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>, Integer>() {
                        @Override
                        public Integer getKey(Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> in) throws Exception {
                            return in.f0;
                        }
                    })
                    .equalTo(new KeySelector<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>, Integer>() {
                        @Override
                        public Integer getKey(Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> inn) throws Exception {
                            return inn.f0;
                        }
                    })
                    .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                    .apply(new JoinFunction<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>>() {
                        @Override
                        public Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> join(Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> tcc, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> tp) throws Exception {
                            return new Tuple23<>(tcc.f0,tcc.f1, tcc.f2, tcc.f3, tcc.f4, tcc.f5, tcc.f6, tcc.f7, tcc.f8, tcc.f9, tcc.f10, tcc.f11, tp.f2, tp.f3, tp.f4, tp.f5, tp.f6, tp.f7, tp.f8, tp.f9, tp.f10,tp.f11, tp.f12);
                        }
                    });

            DataStream<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> resultStream = inputStream
                    .flatMap(new CheckStroke());


            resultStream.print();

            streamEnv.execute();

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static class CheckStroke extends RichFlatMapFunction<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>, Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> {

        private transient ValueState<double[]> coefficientsState;


        @Override
        public void open(Configuration config) throws Exception {
            ValueStateDescriptor<double[]> descriptor = new ValueStateDescriptor<>(
                    "coefficients",
                    TypeInformation.of(new TypeHint<double[]>() {})
            );
            coefficientsState = getRuntimeContext().getState(descriptor);

            double[] initialCoefficients = new double[11];
            Arrays.fill(initialCoefficients, 0.1);
            coefficientsState.update(initialCoefficients);

        }

        @Override
        public void flatMap(Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> input, Collector<Tuple23<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>> out) throws Exception {

            double[] coefficients = coefficientsState.value();
            if(coefficients == null){
                System.err.println("coefficients is null");
                return;
            }

            double[] data = new double[]{
                    input.f1, input.f2, input.f3, input.f4, input.f5, input.f6, input.f7, input.f8, input.f9, input.f10, input.f11
            };

            double result = 0;
            for (int i = 0; i < data.length; i++) {
                result += data[i] * coefficients[i+1];
            }

            String strokeStatus = (Math.abs(result - 1) < 0.1) ? "Stroke" : "Normal";


            out.collect(strokeStatus);
        }
    }


}