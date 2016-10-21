#include <iostream>
#include <cmath>

using namespace std;

typedef struct team{

char tColor;
int nPlayers;
int tPoints;
int *pPoints;
int *pId;

}t_team;


class session{

    t_team team1;
    t_team team2;
    int gameSize;
    int nLoc;
    double rad;
    double s_rad;
    double start_x;
    double start_y;
    double **locArr = new double*[2];
    int *locPoint;
    char *locColor;

/////////////////////////////////////////
// generates the gps locations for flags
    void genLocArr(){
        //double x,y;
        locArr[1] = new double[nLoc];
        locArr[2] = new double[nLoc];
        locPoint = new int[nLoc];
        locColor = new char[nLoc];
        // do the generation
        double delTheta = 2*3.1415/nLoc;
        int i;
        for(i=1;i<=nLoc;i++){
            locArr[1][i-1] = start_x+(i*rad*1.0/nLoc)*(cos(i*delTheta));
            locArr[2][i-1] = start_y+(i*rad*1.0/nLoc)*(sin(i*delTheta));
        }
        return;
    }
/////////////////////////////////////////
// if someone enters a base, it either adds or subtracts
    void conquerBase(int tmNum, int id, double x, double y){
        int i;
        bool check=false;
        t_team *tm;
        if (tmNum == 1) tm = &team1;
        else tm = &team2;
        for(i=0;i<nLoc;i++){
            if (locArr[1][i] == x && locArr[2][i] == y){
                if (locPoint==0){
                        locColor[i]=(*tm).tColor;
                    }
                if(tmNum==1) locPoint[i]++;
                else locPoint[i]--;
                check = true;
                break;
            }
        }
        if (!check) return;
        // looks for player and adds to their score as well as the teams
        for(i=0;i<(*tm).nPlayers;i++){
            if (id==*((*tm).pId)+i){
                (*((*tm).pPoints+i))++;
                (*tm).tPoints++;
                break;
            }//endif
        }//endfor
    }
/////////////////////////////////////////
// sets different aspects
    void setRadius(double r){rad=r;}
    void setBaseRadius(double r){s_rad=r;}
/////////////////////////////////////////
// returns different aspects
    double **getLoc(){return locArr;}
    char *getLocColor(){return locColor;}
    t_team getTeam(int tmNum){if (tmNum==1) return team1;
        else return team2;}
    int getGameSize(){return gameSize;}

/////////////////////////////////////////
    void restartGame(){// needs to be completed
    }
/////////////////////////////////////////
    void inByTeam(t_team *tm_1, t_team *tm_2){
    team1 = *(tm_1);
    team2 = *(tm_2);
    }

/////////////////////////////////////////

};

int main()
{
    /*session first;
    first.nLoc = 5;
    first.rad = 5;
    first.start_x=0;
    first.start_y=0;

    t_team a;
    t_team b;

    first.inByTeam(&a,&b);

    first.genLocArr();
    double** chkLoc = first.getLoc();
    int i;
    for(i=0;i<5;i++){
        cout<<chkLoc[1][i]<<", "<<chkLoc[2][i]<<endl;
    }*/

    return 0;
}
