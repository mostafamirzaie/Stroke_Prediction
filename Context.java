public class Context {
    public Long Id;
    public Long Age;
    public Long Gender;
    public Long Hypertension;
    public Long Heart_Disease;
    public Long Ever_Married;
    public Long Work_Type;
    public Long Residence_Type;
    public Long Average_Glucose_Level;
    public Long BMI;
    public Long Smoking_Status;
    public Long Physical_Activity;
    public Long Dietary_Habits;
    public Long Alcohol_Consumption;
    public Long Chronic_Stress;
    public Long Sleep_Hours;
    public Long Family_History_of_Stroke;
    public Long Education_Level;
    public Long Income_Level;
    public Long Stroke_Risk_Score;
    public Long Region;
    public Long Stroke_Occurrence;

    public Context(long Id, long Age, long Gender, long Hypertension, long Heart_Disease, long Ever_Married, long Work_Type, long Residence_Type, long Average_Glucose_Level, long BMI, long Smoking_Status, long Physical_Activity, long Dietary_Habits, long Alcohol_Consumption, long Chronic_Stress, long Sleep_Hours, long Family_History_of_Stroke, long Education_Level, long Income_Level, long Stroke_Risk_Score, long Region, long Stroke_Occurrence)
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
        this.Stroke_Occurrence = Stroke_Occurrence;
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
                + " |" + this.Region
                + " |" + this.Stroke_Occurrence + " > ";
    }
}
