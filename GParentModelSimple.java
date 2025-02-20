public class GParentModelSimple {
    public double Age;
    public double Stroke_Occurrence;

    public GParentModelSimple(double Age, double Stroke_Occurrence)
    {
        this.Age = Age;
        this.Stroke_Occurrence = Stroke_Occurrence;
    }

    public String toString()
    {
        return    " |" + this.Age
                + " |" + this.Stroke_Occurrence + " > ";
    }
}
