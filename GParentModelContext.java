public class GParentModelContext {
    public double Id;
    public double Age;
    public double Gender;
    public double Hypertension;
    public double Heart_Disease;
    public double Ever_Married;
    public double Work_Type;
    public double Residence_Type;
    public double Average_Glucose_Level;
    public double BMI;
    public double Smoking_Status;
    public double Physical_Activity;
    public double Dietary_Habits;
    public double Alcohol_Consumption;
    public double Chronic_Stress;
    public double Sleep_Hours;
    public double Family_History_of_Stroke;
    public double Education_Level;
    public double Income_Level;
    public double Stroke_Risk_Score;
    public double Region;

    public GParentModelContext(double Id, double Age, double Gender, double Hypertension, double Heart_Disease, double Ever_Married, double Work_Type, double Residence_Type, double Average_Glucose_Level, double BMI, double Smoking_Status, double Physical_Activity, double Dietary_Habits, double Alcohol_Consumption, double Chronic_Stress, double Sleep_Hours, double Family_History_of_Stroke, double Education_Level, double Income_Level, double Stroke_Risk_Score, double Region)
    {
        this.Id = Id;
        this.Age = Age;
        this.Gender = Gender;
        this.Hypertension = Hypertension;
        this.Heart_Disease = Heart_Disease;
        this.Ever_Married = Ever_Married;
        this.Work_Type = Work_Type;
        this.Residence_Type = Residence_Type;
        this.Average_Glucose_Level = Average_Glucose_Level;
        this.BMI = BMI;
        this.Smoking_Status = Smoking_Status;
        this.Physical_Activity = Physical_Activity;
        this.Dietary_Habits = Dietary_Habits;
        this.Alcohol_Consumption = Alcohol_Consumption;
        this.Chronic_Stress = Chronic_Stress;
        this.Sleep_Hours = Sleep_Hours;
        this.Family_History_of_Stroke = Family_History_of_Stroke;
        this.Education_Level = Education_Level;
        this.Income_Level = Income_Level;
        this.Stroke_Risk_Score = Stroke_Risk_Score;
        this.Region = Region;
    }

    public String toString()
    {
        return    "  < " + this.Id
                + " |" + this.Age
                + " |" + this.Gender
                + " |" + this.Hypertension
                + " |" + this.Heart_Disease
                + " |" + this.Ever_Married
                + " |" + this.Work_Type
                + " |" + this.Residence_Type
                + " |" + this.Average_Glucose_Level
                + " |" + this.BMI
                + " |" + this.Smoking_Status
                + " |" + this.Physical_Activity
                + " |" + this.Dietary_Habits
                + " |" + this.Alcohol_Consumption
                + " |" + this.Chronic_Stress
                + " |" + this.Sleep_Hours
                + " |" + this.Family_History_of_Stroke
                + " |" + this.Education_Level
                + " |" + this.Income_Level
                + " |" + this.Stroke_Risk_Score
                + " |" + this.Region + " > ";
    }
}
