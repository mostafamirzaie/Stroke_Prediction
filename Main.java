import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main
{
    public static void main(String[] args) throws IOException, ParseException, SQLException, InterruptedException {

        String line = "";
        String splitBy = ",";
        ArrayList<Patients> patient = new ArrayList<Patients>();
        BufferedReader br = new BufferedReader(new FileReader("Stroke_Prediction_Indians.csv"));
        StringBuilder sqlCommand = new StringBuilder();
        String sqlCommand2 = "";
        StringBuilder sqlCommand1 = new StringBuilder();
        SQLDatabaseConnection MySql = new SQLDatabaseConnection();

        while ((line = br.readLine()) != null)   //returns a Boolean value
        {
            String[] sensor = line.split(splitBy);    // use comma as separator
            int id = Integer.parseInt(sensor[0]);
            int age = Integer.parseInt(sensor[1]);
            patient.add(new Patients(id, age));
        }

        Grid outputGrid = new Grid(patient, 1, 1, new double[]{0, 100});

        ////Save Context Models in each Grid

        ResultSet Ans = MySql.Select("SELECT [gIndex]" +
                                    "      ,[gLevel]" +
                                    "      ,[Id]" +
                                    "      ,[Age]" +
                                    "      ,[Gender]" +
                                    "      ,[Hypertension]" +
                                    "      ,[Heart_Disease]" +
                                    "      ,[Ever_Married]" +
                                    "      ,[Work_Type]" +
                                    "      ,[Residence_Type]" +
                                    "      ,[Average_Glucose_Level]" +
                                    "      ,[BMI]" +
                                    "      ,[Smoking_Status]" +
                                    "      ,[Physical_Activity]" +
                                    "      ,[Dietary_Habits]" +
                                    "      ,[Alcohol_Consumption]" +
                                    "      ,[Chronic_Stress]" +
                                    "      ,[Sleep_Hours]" +
                                    "      ,[Family_History_of_Stroke]" +
                                    "      ,[Education_Level]" +
                                    "      ,[Income_Level]" +
                                    "      ,[Stroke_Risk_Score]" +
                                    "      ,[Region]" +


                                    "  FROM [GridTest].[dbo].[GradientBoosting]" +
                                    "  group by [gIndex]" +
                                    "      ,[gLevel]" +
                                    "      ,[Id]" +
                                    "      ,[Age]" +
                                    "      ,[Gender]" +
                                    "      ,[Hypertension]" +
                                    "      ,[Heart_Disease]" +
                                    "      ,[Ever_Married]" +
                                    "      ,[Work_Type]" +
                                    "      ,[Residence_Type]" +
                                    "      ,[Average_Glucose_Level]" +
                                    "      ,[BMI]" +
                                    "      ,[Smoking_Status]" +
                                    "      ,[Physical_Activity]" +
                                    "      ,[Dietary_Habits]" +
                                    "      ,[Alcohol_Consumption]" +
                                    "      ,[Chronic_Stress]" +
                                    "      ,[Sleep_Hours]" +
                                    "      ,[Family_History_of_Stroke]" +
                                    "      ,[Education_Level]" +
                                    "      ,[Income_Level]" +
                                    "      ,[Stroke_Risk_Score]" +
                                    "      ,[Region]" +
                                    "   order by model_gLevel");
        while (Ans.next()) {
            long gIndex = Long.parseLong(Ans.getString(1));
            int gLevel = Integer.parseInt(Ans.getString(2));
            double Id = Double.parseDouble(Ans.getString(3));
            double Age = Double.parseDouble(Ans.getString(4));
            double Gender = Double.parseDouble(Ans.getString(5));
            double Hypertension = Double.parseDouble(Ans.getString(6));
            double Heart_Disease = Double.parseDouble(Ans.getString(7));
            double Ever_Married = Double.parseDouble(Ans.getString(8));
            double Work_Type = Double.parseDouble(Ans.getString(9));
            double Residence_Type = Double.parseDouble(Ans.getString(10));
            double Average_Glucose_Level = Double.parseDouble(Ans.getString(11));
            double BMI = Double.parseDouble(Ans.getString(12));
            double Smoking_Status = Double.parseDouble(Ans.getString(13));
            double Physical_Activity = Double.parseDouble(Ans.getString(14));
            double Dietary_Habits = Double.parseDouble(Ans.getString(15));
            double Alcohol_Consumption = Double.parseDouble(Ans.getString(16));
            double Chronic_Stress = Double.parseDouble(Ans.getString(17));
            double Sleep_Hours = Double.parseDouble(Ans.getString(18));
            double Family_History_of_Stroke = Double.parseDouble(Ans.getString(19));
            double Education_Level = Double.parseDouble(Ans.getString(20));
            double Income_Level = Double.parseDouble(Ans.getString(21));
            double Stroke_Risk_Score = Double.parseDouble(Ans.getString(22));
            double Region = Double.parseDouble(Ans.getString(23));
            GModelContext gModelContext = new GModelContext(Id, Age, Gender, Hypertension, Heart_Disease, Ever_Married, Work_Type, Residence_Type, Average_Glucose_Level, BMI, Smoking_Status, Physical_Activity, Dietary_Habits, Alcohol_Consumption, Chronic_Stress, Sleep_Hours, Family_History_of_Stroke, Education_Level, Income_Level, Stroke_Risk_Score, Region );
            Grid gridTest = outputGrid.SearchByIndex(gIndex);
            gridTest.gModelContexts.add(gModelContext);
        }

        //////////////
        /////// Save Statistics in each Segment
        BufferedReader statistics = new BufferedReader(new FileReader("Statistics.csv"));
        statistics.readLine();
        while ((line = statistics.readLine()) != null)   //returns a Boolean value
        {
            String[] stat = line.split(splitBy);    // use comma as separator
            int ID = Integer.parseInt(stat[0]);
            int Stroke = Integer.parseInt(stat[1]);
            Statistics statistics1 = new Statistics(ID, Stroke);
            Grid gridTest = outputGrid.SearchById(ID);
            gridTest.statistics.add(statistics1);
        }

        ///////////
        /////// Save Context Model in each Segment
        ResultSet SegmentsId = MySql.Select(" SELECT id from Patients");
        while (SegmentsId.next()) {
            int id = Integer.parseInt(SegmentsId.getString(1));

            ResultSet PatOwnContext = MySql.Select("SELECT top 1 [gLevel]" +
                    "      ,[Id]" +
                    "      ,[Age]" +
                    "      ,[Gender]" +
                    "      ,[Hypertension]" +
                    "      ,[Heart_Disease]" +
                    "      ,[Ever_Married]" +
                    "      ,[Work_Type]" +
                    "      ,[Residence_Type]" +
                    "      ,[Average_Glucose_Level]" +
                    "      ,[BMI]" +
                    "      ,[Smoking_Status]" +
                    "      ,[Physical_Activity]" +
                    "      ,[Dietary_Habits]" +
                    "      ,[Alcohol_Consumption]" +
                    "      ,[Chronic_Stress]" +
                    "      ,[Sleep_Hours]" +
                    "      ,[Family_History_of_Stroke]" +
                    "      ,[Education_Level]" +
                    "      ,[Income_Level]" +
                    "      ,[Stroke_Risk_Score]" +
                    "      ,[Region]" +
                    "      ,[Accuracy]" +
                    "  FROM [GridTest].[dbo].[GradientBoosting]" +
                    "  where [ID] = " + id +
                    "  group by" +
                    "  [gLevel]" +
                    "      ,[Id]" +
                    "      ,[Age]" +
                    "      ,[Gender]" +
                    "      ,[Hypertension]" +
                    "      ,[Heart_Disease]" +
                    "      ,[Ever_Married]" +
                    "      ,[Work_Type]" +
                    "      ,[Residence_Type]" +
                    "      ,[Average_Glucose_Level]" +
                    "      ,[BMI]" +
                    "      ,[Smoking_Status]" +
                    "      ,[Physical_Activity]" +
                    "      ,[Dietary_Habits]" +
                    "      ,[Alcohol_Consumption]" +
                    "      ,[Chronic_Stress]" +
                    "      ,[Sleep_Hours]" +
                    "      ,[Family_History_of_Stroke]" +
                    "      ,[Education_Level]" +
                    "      ,[Income_Level]" +
                    "      ,[Stroke_Risk_Score]" +
                    "      ,[Region]" +
                    "      ,[Accuracy]" +
                    "  having Accuracy = max(Accuracy) and gLevel = min(gLevel)" +
                    "  order by gLevel desc");
            while (PatOwnContext.next()) {
                int gLevel = Integer.parseInt(PatOwnContext.getString(1));
                int parentGLevel = ((gLevel - 1) == 0) ? 1 : gLevel - 1;

                double Id = Double.parseDouble(PatOwnContext.getString(2));
                double Age = Double.parseDouble(PatOwnContext.getString(3));
                double Gender = Double.parseDouble(PatOwnContext.getString(4));
                double Hypertension = Double.parseDouble(PatOwnContext.getString(5));
                double Heart_Disease = Double.parseDouble(PatOwnContext.getString(6));
                double Ever_Married = Double.parseDouble(PatOwnContext.getString(7));
                double Work_Type = Double.parseDouble(PatOwnContext.getString(8));
                double Residence_Type = Double.parseDouble(PatOwnContext.getString(9));
                double Average_Glucose_Level = Double.parseDouble(PatOwnContext.getString(10));
                double BMI = Double.parseDouble(PatOwnContext.getString(11));
                double Smoking_Status = Double.parseDouble(PatOwnContext.getString(12));
                double Physical_Activity = Double.parseDouble(PatOwnContext.getString(13));
                double Dietary_Habits = Double.parseDouble(PatOwnContext.getString(14));
                double Alcohol_Consumption = Double.parseDouble(PatOwnContext.getString(15));
                double Chronic_Stress = Double.parseDouble(PatOwnContext.getString(16));
                double Sleep_Hours = Double.parseDouble(PatOwnContext.getString(17));
                double Family_History_of_Stroke = Double.parseDouble(PatOwnContext.getString(18));
                double Education_Level = Double.parseDouble(PatOwnContext.getString(19));
                double Income_Level = Double.parseDouble(PatOwnContext.getString(20));
                double Stroke_Risk_Score = Double.parseDouble(PatOwnContext.getString(21));
                double Region = Double.parseDouble(PatOwnContext.getString(22));
                GModelContext gModelContext = new GModelContext(Id, Age, Gender, Hypertension, Heart_Disease, Ever_Married, Work_Type, Residence_Type, Average_Glucose_Level, BMI, Smoking_Status, Physical_Activity, Dietary_Habits, Alcohol_Consumption, Chronic_Stress, Sleep_Hours, Family_History_of_Stroke, Education_Level, Income_Level, Stroke_Risk_Score, Region );

                Grid gridTest = outputGrid.SearchById(id);
                gridTest.gModelContexts.add(gModelContext);
                if (parentGLevel == gLevel) {
                    GParentModelContext gParentModelContext = new GParentModelContext(Id, Age, Gender, Hypertension, Heart_Disease, Ever_Married, Work_Type, Residence_Type, Average_Glucose_Level, BMI, Smoking_Status, Physical_Activity, Dietary_Habits, Alcohol_Consumption, Chronic_Stress, Sleep_Hours, Family_History_of_Stroke, Education_Level, Income_Level, Stroke_Risk_Score, Region);
                    gridTest.gParentModelContexts.add(gParentModelContext);
                } else {
                    ResultSet PatParentContext = MySql.Select("SELECT top 1 [gLevel]" +
                            "      ,[Id]" +
                            "      ,[Age]" +
                            "      ,[Gender]" +
                            "      ,[Hypertension]" +
                            "      ,[Heart_Disease]" +
                            "      ,[Ever_Married]" +
                            "      ,[Work_Type]" +
                            "      ,[Residence_Type]" +
                            "      ,[Average_Glucose_Level]" +
                            "      ,[BMI]" +
                            "      ,[Smoking_Status]" +
                            "      ,[Physical_Activity]" +
                            "      ,[Dietary_Habits]" +
                            "      ,[Alcohol_Consumption]" +
                            "      ,[Chronic_Stress]" +
                            "      ,[Sleep_Hours]" +
                            "      ,[Family_History_of_Stroke]" +
                            "      ,[Education_Level]" +
                            "      ,[Income_Level]" +
                            "      ,[Stroke_Risk_Score]" +
                            "      ,[Region]" +
                            "      ,[Accuracy]" +
                            "  FROM [GridTest].[dbo].[GradientBoosting]" +
                            "  where [Id] = " + id + " and gLevel = " + parentGLevel + "" +
                            "  group by" +
                            "  [gLevel]" +
                            "      ,[Id]" +
                            "      ,[Age]" +
                            "      ,[Gender]" +
                            "      ,[Hypertension]" +
                            "      ,[Heart_Disease]" +
                            "      ,[Ever_Married]" +
                            "      ,[Work_Type]" +
                            "      ,[Residence_Type]" +
                            "      ,[Average_Glucose_Level]" +
                            "      ,[BMI]" +
                            "      ,[Smoking_Status]" +
                            "      ,[Physical_Activity]" +
                            "      ,[Dietary_Habits]" +
                            "      ,[Alcohol_Consumption]" +
                            "      ,[Chronic_Stress]" +
                            "      ,[Sleep_Hours]" +
                            "      ,[Family_History_of_Stroke]" +
                            "      ,[Education_Level]" +
                            "      ,[Income_Level]" +
                            "      ,[Stroke_Risk_Score]" +
                            "      ,[Region]" +
                            "      ,[Accuracy]" +
                            "  order by gLevel desc");
                    while (PatParentContext.next()) {

                        Id = Double.parseDouble(PatParentContext.getString(2));
                        Age = Double.parseDouble(PatParentContext.getString(3));
                        Gender = Double.parseDouble(PatParentContext.getString(4));
                        Hypertension = Double.parseDouble(PatParentContext.getString(5));
                        Heart_Disease = Double.parseDouble(PatParentContext.getString(6));
                        Ever_Married = Double.parseDouble(PatParentContext.getString(7));
                        Work_Type = Double.parseDouble(PatParentContext.getString(8));
                        Residence_Type = Double.parseDouble(PatParentContext.getString(9));
                        Average_Glucose_Level = Double.parseDouble(PatParentContext.getString(10));
                        BMI = Double.parseDouble(PatParentContext.getString(11));
                        Smoking_Status = Double.parseDouble(PatParentContext.getString(12));
                        Physical_Activity = Double.parseDouble(PatParentContext.getString(13));
                        Dietary_Habits = Double.parseDouble(PatParentContext.getString(14));
                        Alcohol_Consumption = Double.parseDouble(PatParentContext.getString(15));
                        Chronic_Stress = Double.parseDouble(PatParentContext.getString(16));
                        Sleep_Hours = Double.parseDouble(PatParentContext.getString(17));
                        Family_History_of_Stroke = Double.parseDouble(PatParentContext.getString(18));
                        Education_Level = Double.parseDouble(PatParentContext.getString(19));
                        Income_Level = Double.parseDouble(PatParentContext.getString(20));
                        Stroke_Risk_Score = Double.parseDouble(PatParentContext.getString(21));
                        Region = Double.parseDouble(PatParentContext.getString(22));
                        GParentModelContext gParentModelContext = new GParentModelContext(Id, Age, Gender, Hypertension, Heart_Disease, Ever_Married, Work_Type, Residence_Type, Average_Glucose_Level, BMI, Smoking_Status, Physical_Activity, Dietary_Habits, Alcohol_Consumption, Chronic_Stress, Sleep_Hours, Family_History_of_Stroke, Education_Level, Income_Level, Stroke_Risk_Score, Region );

                        outputGrid.SearchById(id);
                        gridTest.gParentModelContexts.add(gParentModelContext);
                    }
                }

            }
        }

        Grid grid = outputGrid.SearchById(10);
        long gIndex = grid.gIndex;
        System.out.println(gIndex);




        //// Test
        File output = new File("Grid3.txt");
        FileWriter outputWriter = new FileWriter(output);
        ArrayList<Grid> endPoints = outputGrid.SearchByLevel(3);
        int ids;
        for (Grid endPoint: endPoints) {
            if (endPoint.patients.size() >= 1){
                sqlCommand1.append(endPoint.gIndex).append(": ");
                for (int i=0; i<endPoint.patients.size(); i++){
                    ids = endPoint.patients.get(i).id;
                    sqlCommand1.append(ids).append(" | ");
                }
                sqlCommand1.append("\n");
            }
        }
        outputWriter.write(sqlCommand1.toString());
        outputWriter.close();


       int id=0;
        String sqlCommand5 = "";
        BufferedReader brPatient = new BufferedReader(new FileReader("Stroke_Prediction_Indians.csv"));
        brPatient.readLine();

        while ((line = brPatient.readLine()) != null)   //returns a Boolean value
        {
            String[] patients = line.split(splitBy);    // use comma as separator
            if(patients.length == 22) {

                double Id = Double.parseDouble(patients[1]);
                double Age = Double.parseDouble(patients[2]);
                double Gender = Double.parseDouble(patients[3]);
                double Hypertension = Double.parseDouble(patients[4]);
                double Heart_Disease = Double.parseDouble(patients[5]);
                double Ever_Married = Double.parseDouble(patients[6]);
                double Work_Type = Double.parseDouble(patients[7]);
                double Residence_Type = Double.parseDouble(patients[8]);
                double Average_Glucose_Level = Double.parseDouble(patients[9]);
                double BMI = Double.parseDouble(patients[10]);
                double Smoking_Status = Double.parseDouble(patients[11]);
                double Physical_Activity = Double.parseDouble(patients[12]);
                double Dietary_Habits = Double.parseDouble(patients[13]);
                double Alcohol_Consumption = Double.parseDouble(patients[14]);
                double Chronic_Stress = Double.parseDouble(patients[15]);
                double Sleep_Hours = Double.parseDouble(patients[16]);
                double Family_History_of_Stroke = Double.parseDouble(patients[17]);
                double Education_Level = Double.parseDouble(patients[18]);
                double Income_Level = Double.parseDouble(patients[19]);
                double Stroke_Risk_Score = Double.parseDouble(patients[20]);
                double Region = Double.parseDouble(patients[22]);
                double Stroke = Double.parseDouble(patients[23]);
                id++;
                System.out.println(id);
                sqlCommand5 = "INSERT INTO Patients (Id, Age, Gender, Hypertension, Heart_Disease, Ever_Married, Work_Type, Residence_Type, Average_Glucose_Level, BMI, Smoking_Status, Physical_Activity, Dietary_Habits, Alcohol_Consumption, Chronic_Stress, Sleep_Hours, Family_History_of_Stroke, Education_Level, Income_Level, Stroke_Risk_Score, Region, Stroke) VALUES (\'"+Id+"\', "+Age+", "+Gender+" , "+Hypertension+" , "+Heart_Disease+", "+Ever_Married+" , "+Work_Type+" , "+Residence_Type+", "+Average_Glucose_Level+" , "+BMI+" , "+Smoking_Status+", "+Physical_Activity+" , "+Dietary_Habits+" , "+Alcohol_Consumption+", "+Chronic_Stress+" , "+Sleep_Hours+" , "+Family_History_of_Stroke+", "+Education_Level+" , "+Income_Level+" , "+Stroke_Risk_Score+", "+Region+" , "+Stroke+");";
                MySql.Command(sqlCommand5);
            }
        }

        ////////////////DATA SERVER

        /*ServerSocket listener = new ServerSocket(9090);
        br = null;
        try{
            Socket socket = listener.accept();
            System.out.println("Got new connection: " + socket.toString());

            br = new BufferedReader(new FileReader("test.csv"));

            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while((line = br.readLine()) != null){
                    String[] inputs = line.split(",");
                    if(inputs.length == 22){

                        double Id = Double.parseDouble(inputs[0]);
                        double Age = Double.parseDouble(inputs[1]);
                        double Gender = Double.parseDouble(inputs[2]);
                        double Hypertension = Double.parseDouble(inputs[3]);
                        double Heart_Disease = Double.parseDouble(inputs[4]);
                        double Ever_Married = Double.parseDouble(inputs[5]);
                        double Work_Type = Double.parseDouble(inputs[6]);
                        double Residence_Type = Double.parseDouble(inputs[7]);
                        double Average_Glucose_Level = Double.parseDouble(inputs[8]);
                        double BMI = Double.parseDouble(inputs[9]);
                        double Smoking_Status = Double.parseDouble(inputs[10]);
                        double Physical_Activity = Double.parseDouble(inputs[11]);
                        double Dietary_Habits = Double.parseDouble(inputs[12]);
                        double Alcohol_Consumption = Double.parseDouble(inputs[13]);
                        double Chronic_Stress = Double.parseDouble(inputs[14]);
                        double Sleep_Hours = Double.parseDouble(inputs[15]);
                        double Family_History_of_Stroke = Double.parseDouble(inputs[16]);
                        double Education_Level = Double.parseDouble(inputs[17]);
                        double Income_Level = Double.parseDouble(inputs[18]);
                        double Stroke_Risk_Score = Double.parseDouble(inputs[19]);
                        double Region = Double.parseDouble(inputs[20]);
                        double Stroke = Double.parseDouble(inputs[21]);
                        Grid grr = outputGrid.SearchByIdLevel((long) Id, 5);
                        gIndex = grr.gIndex;
                        String outputs = inputs[0] + "," + inputs[1] + "," + inputs[2] + "," + inputs[3]  + "," + inputs[4] + "," + inputs[5] + "," + inputs[6] + "," + inputs[7] + "," + inputs[8] + "," + inputs[9] + "," + inputs[10] + "," + inputs[11] + "," + inputs[12] + "," + inputs[13] + "," + inputs[14] + "," + inputs[15] + "," + inputs[16] + "," + inputs[17] + "," + inputs[18] + "," + inputs[19] + "," + inputs[20] + "," + inputs[21]+ "," + gIndex;

                        //Input input = new Input(date, segId, bus, message);
                        out.println(outputs);
                        Thread.sleep(10);
                    }
                }

            } finally{
                socket.close();
            }

        } catch(Exception e ){
            e.printStackTrace();
        } finally{
            listener.close();
            if (br != null)
                br.close();
        }*/

        ////////////////DATA SERVER 2

        ServerSocket listener = new ServerSocket(9090);
        br = null;
        try{
            Socket socket = listener.accept();
            System.out.println("Got new connection: " + socket.toString());

            br = new BufferedReader(new FileReader("test.csv"));

            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                br.readLine();
                while((line = br.readLine()) != null){
                    String[] inputs = line.split(",");
                    if(inputs.length == 22){
                        double Id = Double.parseDouble(inputs[0]);
                        double Age = Double.parseDouble(inputs[1]);
                        double Gender = Double.parseDouble(inputs[2]);
                        double Hypertension = Double.parseDouble(inputs[3]);
                        double Heart_Disease = Double.parseDouble(inputs[4]);
                        double Ever_Married = Double.parseDouble(inputs[5]);
                        double Work_Type = Double.parseDouble(inputs[6]);
                        double Residence_Type = Double.parseDouble(inputs[7]);
                        double Average_Glucose_Level = Double.parseDouble(inputs[8]);
                        double BMI = Double.parseDouble(inputs[9]);
                        double Smoking_Status = Double.parseDouble(inputs[10]);
                        double Physical_Activity = Double.parseDouble(inputs[11]);
                        double Dietary_Habits = Double.parseDouble(inputs[12]);
                        double Alcohol_Consumption = Double.parseDouble(inputs[13]);
                        double Chronic_Stress = Double.parseDouble(inputs[14]);
                        double Sleep_Hours = Double.parseDouble(inputs[15]);
                        double Family_History_of_Stroke = Double.parseDouble(inputs[16]);
                        double Education_Level = Double.parseDouble(inputs[17]);
                        double Income_Level = Double.parseDouble(inputs[18]);
                        double Stroke_Risk_Score = Double.parseDouble(inputs[19]);
                        double Region = Double.parseDouble(inputs[20]);
                        double Stroke = Double.parseDouble(inputs[21]);
                        /// gIndex extraction
                        Grid grr = outputGrid.SearchById(id);
                        gIndex = grr.gIndex;
                        ///Own Context model extraction
                        double M_Id = grr.gModelContexts.get(0).Id;
                        double M_Age = grr.gModelContexts.get(0).Age;
                        double M_Gender = grr.gModelContexts.get(0).Gender;
                        double M_Hypertension = grr.gModelContexts.get(0).Hypertension;
                        double M_Heart_Disease = grr.gModelContexts.get(0).Heart_Disease;
                        double M_Ever_Married = grr.gModelContexts.get(0).Ever_Married;
                        double M_Work_Type = grr.gModelContexts.get(0).Work_Type;
                        double M_Residence_Type = grr.gModelContexts.get(0).Residence_Type;
                        double M_Average_Glucose_Level = grr.gModelContexts.get(0).Average_Glucose_Level;
                        double M_BMI = grr.gModelContexts.get(0).BMI;
                        double M_Smoking_Status = grr.gModelContexts.get(0).Smoking_Status;
                        double M_Physical_Activity = grr.gModelContexts.get(0).Physical_Activity;
                        double M_Dietary_Habits = grr.gModelContexts.get(0).Dietary_Habits;
                        double M_Alcohol_Consumption = grr.gModelContexts.get(0).Alcohol_Consumption;
                        double M_Chronic_Stress = grr.gModelContexts.get(0).Chronic_Stress;
                        double M_Sleep_Hours = grr.gModelContexts.get(0).Sleep_Hours;
                        double M_Family_History_of_Stroke = grr.gModelContexts.get(0).Family_History_of_Stroke;
                        double M_Education_Level = grr.gModelContexts.get(0).Education_Level;
                        double M_Income_Level = grr.gModelContexts.get(0).Income_Level;
                        double M_Stroke_Risk_Score = grr.gModelContexts.get(0).Stroke_Risk_Score;
                        double M_Region = grr.gModelContexts.get(0).Region;

                        ///Parent Context model extraction
                        double P_Id = grr.gParentModelContexts.get(0).Id;
                        double P_Age = grr.gParentModelContexts.get(0).Age;
                        double P_Gender = grr.gParentModelContexts.get(0).Gender;
                        double P_Hypertension = grr.gParentModelContexts.get(0).Hypertension;
                        double P_Heart_Disease = grr.gParentModelContexts.get(0).Heart_Disease;
                        double P_Ever_Married = grr.gParentModelContexts.get(0).Ever_Married;
                        double P_Work_Type = grr.gParentModelContexts.get(0).Work_Type;
                        double P_Residence_Type = grr.gParentModelContexts.get(0).Residence_Type;
                        double P_Average_Glucose_Level = grr.gParentModelContexts.get(0).Average_Glucose_Level;
                        double P_BMI = grr.gParentModelContexts.get(0).BMI;
                        double P_Smoking_Status = grr.gParentModelContexts.get(0).Smoking_Status;
                        double P_Physical_Activity = grr.gParentModelContexts.get(0).Physical_Activity;
                        double P_Dietary_Habits = grr.gParentModelContexts.get(0).Dietary_Habits;
                        double P_Alcohol_Consumption = grr.gParentModelContexts.get(0).Alcohol_Consumption;
                        double P_Chronic_Stress = grr.gParentModelContexts.get(0).Chronic_Stress;
                        double P_Sleep_Hours = grr.gParentModelContexts.get(0).Sleep_Hours;
                        double P_Family_History_of_Stroke = grr.gParentModelContexts.get(0).Family_History_of_Stroke;
                        double P_Education_Level = grr.gParentModelContexts.get(0).Education_Level;
                        double P_Income_Level = grr.gParentModelContexts.get(0).Income_Level;
                        double P_Stroke_Risk_Score = grr.gParentModelContexts.get(0).Stroke_Risk_Score;
                        double P_Region = grr.gParentModelContexts.get(0).Region;

                        ///Own Simple model extraction
                        double SM_age = grr.gModelSimples.get(0).Age;
                        double SM_stroke = grr.gModelSimples.get(0).Stroke_Occurrence;

                        ///Parent Traffic model extraction
                        double SP_age = grr.gParentModelSimples.get(0).Age;
                        double SP_stroke = grr.gParentModelSimples.get(0).Stroke_Occurrence;

                        ///// Min and Max of the last year
                        double total = 0;
                        ArrayList<Statistics> test = outputGrid.SearchById(id).statistics;
                        for (Statistics t: test){
                            if (t.age == Age){
                                total = t.total;
                            }
                        }

                        String output1 = gIndex + "," + id + "," + Age + "," + Gender + "," + Hypertension + "," + Heart_Disease + "," + Ever_Married + "," + Work_Type + "," + Residence_Type + "," + Average_Glucose_Level + "," + BMI + "," + Smoking_Status + "," + Physical_Activity + "," + Dietary_Habits + "," + Alcohol_Consumption + "," + Chronic_Stress + "," + Sleep_Hours + "," + Family_History_of_Stroke + "," + Education_Level + "," + Income_Level + "," + Stroke_Risk_Score + "," + Region + "," + Stroke;
                        String output2 = gIndex + "," + id + "," + M_Id + "," + M_Age + "," + M_Gender + "," + M_Hypertension + "," + M_Heart_Disease + "," + M_Ever_Married + "," + M_Work_Type + "," + M_Residence_Type + "," + M_Average_Glucose_Level + "," + M_BMI + "," + M_Smoking_Status + "," + M_Physical_Activity + "," + M_Dietary_Habits + "," + M_Alcohol_Consumption + "," + M_Chronic_Stress + "," + M_Sleep_Hours + "," + M_Family_History_of_Stroke + "," + M_Education_Level + "," + M_Income_Level + "," + M_Stroke_Risk_Score + "," + M_Region ;
                        String output3 = gIndex + P_Id + "," + P_Age + "," + P_Gender + "," + P_Hypertension + "," + P_Heart_Disease + "," + P_Ever_Married + "," + P_Work_Type + "," + P_Residence_Type + "," + P_Average_Glucose_Level + "," + P_BMI + "," + P_Smoking_Status + "," + P_Physical_Activity + "," + P_Dietary_Habits + "," + P_Alcohol_Consumption + "," + P_Chronic_Stress + "," + P_Sleep_Hours + "," + P_Family_History_of_Stroke + "," + P_Education_Level + "," + P_Income_Level + "," + P_Stroke_Risk_Score + "," + P_Region + "," + total;

                        out.println(output1);
                        out.println(output2);
                        out.println(output3);
                        Thread.sleep(10);
                    }
                }

            } finally{
                socket.close();
            }

        } catch(Exception e ){
            e.printStackTrace();
        } finally{
            listener.close();
            if (br != null)
                br.close();
        }

    }
}