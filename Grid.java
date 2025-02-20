import java.util.ArrayList;
import java.util.Stack;

class Grid
{
    public int level;
    public long gIndex;
    public double[] border;
    public ArrayList<Patients> patients;
    public ArrayList<Grid> subGrids;
    //Contextual Attributes
    public ArrayList<Context> contexts = new ArrayList<>();
    public ArrayList<GModelContext> gModelContexts = new ArrayList<GModelContext>();
    public ArrayList<GParentModelContext> gParentModelContexts = new ArrayList<GParentModelContext>();
    public ArrayList<GModelSimple> gModelSimples = new ArrayList<GModelSimple>();
    public ArrayList<GParentModelSimple> gParentModelSimples = new ArrayList<>();
    public ArrayList<Statistics> statistics = new ArrayList<>();

    public Grid(ArrayList<Patients> patients, int level, long gIndex, double[] border)
    {
        this.patients = (patients != null)? patients : new ArrayList<>();
        this.level = (level > 0)? level : 1;
        this.gIndex = (gIndex > 0)? gIndex : 1;
        this.border = (border != null)? border : selfBorder();
        selfEvaluate();
        this.subGrids = (this.patients.size() > 1)? selfFracture() : new ArrayList<>();
        //selfModelize();
    }

    public double[] selfBorder()
    {
        double minAge = 0, maxAge = 0;

        if( this.patients.size() > 0){
            minAge = patients.get(0).age;
            maxAge = patients.get(0).age;

            for(Patients patient : this.patients){
                if(maxAge <= patient.age){ maxAge = patient.age; }
                else if(minAge >= patient.age) { minAge = patient.age; }
            }
        }

        return new double[]{minAge, maxAge};
    }

    public ArrayList<Grid> selfFracture()
    {
        if(this.patients.size() < 1)
            return new ArrayList<>(); //double checking patient existence and preventing infinite loop

        double meanAge = (this.border[0] + this.border[1])/2;

        ArrayList<double[]> subBorders = new ArrayList<>();
        subBorders.add(new double[]{this.border[0], meanAge});
        subBorders.add(new double[]{meanAge, this.border[1]});

        ArrayList<Grid> sGrids = new ArrayList<>();

        String s1 = Long.toString(this.gIndex);
        String s2 = Integer.toString(1);

        // Concatenate both strings
        String s = s1 + s2;

        // Convert the concatenated string
        // to integer
        long cIndex = Long.parseLong(s);
        int sLevel = this.level + 1;

        sGrids.add(new Grid(this.patients, sLevel, cIndex++, subBorders.get(0)));
        sGrids.add(new Grid(this.patients, sLevel, cIndex++, subBorders.get(1)));

        return sGrids;
    }

    public void selfEvaluate()
    {
        if(this.patients.size() < 1) return;
        ArrayList<Patients> newPatient = new ArrayList<>();
        for(Patients patient : this.patients)
            if(IsInBorder(patient.age)) newPatient.add(patient);

        this.patients = newPatient;
    }

   /* public void selfModelize()
    {
        // Check DB and extract relative model
        // Check if has child reCall itself
        if(this.sos.size() < 1) return;
        ArrayList<Sensor> newSos = new ArrayList<>();
        for(Sensor sensor : this.sos)
            if(IsInBorder(new double[]{sensor.latitude, sensor.longitude})) newSos.add(sensor);

        this.sos = newSos;
    }*/

    public Grid SearchByIndex(long index){
        if(this.gIndex == index) return this;

        // Removes current index from the first position of searched index
        String subIndex = Long.toString(index).substring(Long.toString(this.gIndex).length(), Long.toString(index).length());

        // Decides which subGrid to open and search;
        int nextChild = Character.getNumericValue(subIndex.charAt(0));
        return this.subGrids.get(nextChild - 1).SearchByIndex(index);
    }

    public Grid SearchById(long id){
        if(this.patients.get(0).id == id && this.patients.size() == 1) return this;
        for(Grid subGrid : this.subGrids)
            for(Patients sensor : subGrid.patients)
                if(sensor.id == id) return subGrid.SearchById(id);
        return null;
    }

    public Grid SearchByIdLevel(long id, int level){
        for (Patients patient: this.patients)
            if(patient.id == id && this.level == level) return this;
        for(Grid subGrid : this.subGrids)
            for(Patients patient : subGrid.patients)
                if(patient.id == id) return subGrid.SearchByIdLevel(id, level);
        return null;
    }

    public Grid SearchByAge(double age){
        if(this.IsInBorder(age) && this.patients.size() <= 1) return this;
        for(Grid subGrid : this.subGrids)
            if(subGrid.IsInBorder(age)) return subGrid.SearchByAge(age);
        return null;
    }
    public Grid SearchByAgeLevel(double age, int level){
        if(this.IsInBorder(age) && this.level == level) return this;
        for(Grid subGrid : this.subGrids)
            if(subGrid.IsInBorder(age)) return subGrid.SearchByAgeLevel(age, level);
        return null;
    }


    public ArrayList<Grid> SearchByLevel(int level){
        ArrayList<Grid> levelPoints = new ArrayList<>();
        Stack<Grid> gridStack = new Stack<>();
        gridStack.push(this);

        while (!gridStack.empty()){
            Grid iGrid = gridStack.pop();
            if(iGrid.level == level) levelPoints.add(iGrid);
            else if(iGrid.level < level)
                for(Grid subGrid : iGrid.subGrids)
                    gridStack.push(subGrid);
        }

        return levelPoints;
    }

    public boolean IsInBorder(double age)
    {
        if(    age >= this.border[0]
                && age < this.border[1]) return true;
        else return false;
    }

    public ArrayList<Grid> FindEndPoint(){
        ArrayList<Grid> endPoints = new ArrayList<>();
        Stack<Grid> gridStack = new Stack<>();
        gridStack.push(this);

        while (!gridStack.empty()){
            Grid iGrid = gridStack.pop();
            if(iGrid.subGrids.size() == 0) endPoints.add(iGrid);
            else
                for(Grid subGrid : iGrid.subGrids)
                    gridStack.push(subGrid);
        }

        return endPoints;
    }

    public String toString()
    {
        String indent = "";
        for(int i = 0; i < level - 1; i++){ indent = indent + "\t"; }

        String lastIndent = (indent.length() > 1)? indent.substring(0, indent.length() - 1) : "";

        return  "\n" + indent + "Grid Level: " + this.level + "\n"
                + indent + "Grid Index: " + this.gIndex + "\n"
                + indent + "Borders: <" + this.border[0]
                + " | " + this.border[1]
                + "> " + ">\n"
                + indent + "Patient(s): " + this.patients + "\n"
                + indent + "Context(s): " + this.contexts + "\n"
                + indent + "ContextModel(s): " + this.gModelContexts + "\n"
                + indent + "ParentContextModel(s): " + this.gParentModelContexts + "\n"
                + indent + "TrafficModel(s): " + this.gModelSimples + "\n"
                + indent + "ParentTrafficModel(s): " + this.gParentModelSimples + "\n"
                + indent + "Statistic(s): " + this.statistics + "\n"
                + indent + "subGrids: \n" + indent + this.subGrids + "\n" + lastIndent;
    }
}