public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }
    public Body(Body body){
        xxPos=body.xxPos;
        yyPos=body.yyPos;
        xxVel=body.xxVel;
        yyVel=body.yyVel;
        mass=body.mass;
        imgFileName=body.imgFileName;
    }
}
