public class NBody {
    public static double readRadius(String s){
        In in = new In(s);
        int firstItem = in.readInt();
        double secItem = in.readDouble();
        return secItem;
    }

    public static Planet[] readPlanets(String s){
        In in = new In(s);
        int count = in.readInt();
        in.readDouble();
        Planet[] planets= new Planet[count];
        for(int i=0;i<count;i++){
            double xxPos=in.readDouble();
            double yyPos=in.readDouble();
            double xxVel=in.readDouble();
            double yyVel=in.readDouble();
            double mass=in.readDouble();
            String img=in.readString();
            planets[i]=new Planet(xxPos,yyPos,xxVel, yyVel, mass,img);
        }
        return planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double universRadius=readRadius(filename);
        StdDraw.enableDoubleBuffering();
        int cnt = planets.length;
        StdDraw.setScale(-universRadius,universRadius);

        double time=0;
        while(time<T){
            double xForces[] = new double[cnt];
            double yForces[] = new double[cnt];
            for(int i=0;i<cnt;i++){
                xForces[i]=planets[i].calcNetForceExertedByX(planets);
                yForces[i]=planets[i].calcNetForceExertedByY(planets);
            }

            for(int i=0;i<cnt;i++){
                planets[i].update(dt,xForces[i],yForces[i]);
            }

            StdDraw.picture(0,0,"images/starfield.jpg");
            for(Planet p:planets){
                StdDraw.picture(p.xxPos,p.yyPos,"images/"+p.imgFileName);
            }
            StdDraw.show();
            StdDraw.pause(1);
            time+=dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
