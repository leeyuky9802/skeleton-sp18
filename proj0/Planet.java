public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }
    public Planet(Planet body){
        xxPos=body.xxPos;
        yyPos=body.yyPos;
        xxVel=body.xxVel;
        yyVel=body.yyVel;
        mass=body.mass;
        imgFileName=body.imgFileName;
    }
    public double calcDistance(Planet planet){
        double dx=planet.xxPos-xxPos;
        double dy=planet.yyPos-yyPos;
        double rr=dx*dx + dy*dy;
        double r=Math.sqrt(rr);
        return r;
    }
    public double calcForceExertedBy(Planet planet){
        return G*planet.mass*mass/(calcDistance(planet)*calcDistance(planet));
    }

    public double calcForceExertedByX(Planet planet){
        return calcForceExertedBy(planet)*(-xxPos+planet.xxPos)/calcDistance(planet);
    }

    public double calcForceExertedByY(Planet planet){
        return calcForceExertedBy(planet)*(-yyPos+planet.yyPos)/calcDistance(planet);
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double netX=0;
        for(Planet p:planets){
            if(p.equals(this)) continue;
            netX+=calcForceExertedByX(p);
        }
        return netX;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double netY=0;
        for(Planet p:planets){
            if(p.equals(this)) continue;
            netY+=calcForceExertedByY(p);
        }
        return netY;
    }

    public void update(double dt, double xF, double yF){
        xxVel+=dt*xF/mass;
        yyVel+=dt*yF/mass;
        xxPos+=xxVel*dt;
        yyPos+=yyVel*dt;
    }

}
