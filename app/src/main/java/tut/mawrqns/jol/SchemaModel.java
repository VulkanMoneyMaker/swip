package tut.mawrqns.jol;



public class SchemaModel {
    private String title;
    private boolean isUnable;
    private int numberSchema;


    public int getNumberSchema() {
        return numberSchema;
    }

    public void setNumberSchema(int numberSchema) {
        this.numberSchema = numberSchema;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUnable() {
        return isUnable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnable(boolean unable) {
        isUnable = unable;
    }
}
