package creo.com.myapplication;

public class TripPojo {

    public String price;
    public String date;
    public String source;
    public  String dest;

    public TripPojo(String price, String date, String dest,String source) {
       this.price=price;
       this.date=date;
       this.dest=dest;
       this.source=source;
    }


    public String getDate() {
        return date;
    }

    public String getDest() {
        return dest;
    }

    public String getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
