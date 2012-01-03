/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.elapco.ipapp;

/**
 *
 * @author Pablo
 */
public class DirIP {
private int oct[]= new int [4];
private int msk[]= new int [4];


private String dirStr;
private String maskStr;



public DirIP(){
}

public DirIP(String direccion){
this.dirStr=direccion;
}

public void setDir(String direccion) throws IllegalArgumentException{
int indiceS=0;
int indiceF=0;
int i=0;
int tmp[]=new int [4];

    this.contarPuntos(direccion);
    for (i=0;i<=2;i++){
        indiceF=direccion.indexOf(".",indiceS);
        tmp[i]=Integer.valueOf(direccion.substring(indiceS, indiceF));
        indiceS=indiceF+1;
    }
    tmp[3]=Integer.valueOf(direccion.substring(indiceS));
    this.validarValores(tmp);

    for (i=0;i<=3;i++){
            this.oct[i]=tmp[i];
        }
    this.dirStr=direccion;
}


public void setMask(String direccion) throws IllegalArgumentException{
int indiceS=0;
int indiceF=0;
int i=0;
int tmp[]=new int [4];
int contador;
String mskTmp="1";

if (direccion.length()<3){
        if(Integer.valueOf(direccion)>0&&Integer.valueOf(direccion)<32){
        contador=Integer.valueOf(direccion);
            for (i=0;i<(contador-1);i++){
               mskTmp+="1";
            }
            for (i=0;i<(32-contador);i++){
               mskTmp+="0";
            }
    this.msk[0]=Integer.parseInt(mskTmp.substring(0,8), 2);
    this.msk[1]=Integer.parseInt(mskTmp.substring(8,16), 2);
    this.msk[2]=Integer.parseInt(mskTmp.substring(16,24), 2);
    this.msk[3]=Integer.parseInt(mskTmp.substring(24,32), 2);
    this.maskStr=(String.valueOf(this.msk[0])+"."+String.valueOf(this.msk[1])+"."+String.valueOf(this.msk[2])+"."+String.valueOf(this.msk[3]));

        }else{
        throw new IllegalArgumentException ("Longitud de prefijo no válida");
        }


}else{

    this.contarPuntos(direccion);
    for (i=0;i<=2;i++){
        indiceF=direccion.indexOf(".",indiceS);
        tmp[i]=Integer.valueOf(direccion.substring(indiceS, indiceF));
        indiceS=indiceF+1;
    }
    tmp[3]=Integer.valueOf(direccion.substring(indiceS));
    this.validarMascara(tmp);

    for (i=0;i<=3;i++){
            this.msk[i]=tmp[i];
        }
    this.maskStr=direccion;
}
}




private void contarPuntos(String direccion)throws IllegalArgumentException{
int index=0;
int count=0;

index=direccion.indexOf(".",index);
while (index!=-1){
count++;
index++;
index=direccion.indexOf(".",index);
}
if (count!=3){
throw new IllegalArgumentException("Dirección nula o incompleta");
}
}

private void validarValores(int [] tmp)throws IllegalArgumentException{
int i;
 for (i=0;i<=3;i++){
        if (tmp[i]<0 || tmp[i]>255){
            throw new IllegalArgumentException("Rango no válido en uno o más octetos");
        }
    }
}


private void validarMascara(int [] tmp)throws IllegalArgumentException{
int i;
int j;
 if (tmp[0]==0){
                  throw new IllegalArgumentException("Máscara no válida");
             }
 for (i=0;i<=2;i++){
        if (tmp[i]!=0 &&tmp[i]!=128 &&tmp[i]!=192 &&tmp[i]!=224 &&tmp[i]!=240 &&tmp[i]!=248 &&tmp[i]!=252 &&tmp[i]!=254 &&tmp[i]!=255){
            throw new IllegalArgumentException("Máscara no válida");
        }
     if (tmp[i]<255&&i<3){
         for (j=i+1;j<=3;j++){
             if (tmp[j]!=0){
                  throw new IllegalArgumentException("Máscara no válida");
             }
         }
         break;
     }
    }
if (tmp[3]!=0 &&tmp[3]!=128 &&tmp[3]!=192 &&tmp[3]!=224 &&tmp[3]!=240 &&tmp[3]!=248 &&tmp[3]!=252){
            throw new IllegalArgumentException("Máscara no válida");
    }
}



public String getMaskString(){
return this.maskStr;
}

public String getDirString(){
return this.dirStr;
}

private String to8BitString(int number)throws IllegalArgumentException{
if (number<0||number>255){
throw new IllegalArgumentException("Numero fuera del rango de 0-255");
}
else{
String tmp=Integer.toBinaryString(number);
int count=tmp.length();
String tmp2="";
for (int i=0;i<8-count;i++){
    tmp2+="0";
}
return tmp2+tmp;
}
}

public int getLongPrefijo(){
String tmp = this.to8BitString(this.msk[0])+this.to8BitString(this.msk[1])+this.to8BitString(this.msk[2])+this.to8BitString(this.msk[3]);
return tmp.indexOf("0");
}

public String getMaskBinString(){
String tmp = this.to8BitString(this.msk[0])+"."+this.to8BitString(this.msk[1])+"."+this.to8BitString(this.msk[2])+"."+this.to8BitString(this.msk[3]);
return tmp;
}

public String getDirBinString(){
String tmp = this.to8BitString(this.oct[0])+"."+this.to8BitString(this.oct[1])+"."+this.to8BitString(this.oct[2])+"."+this.to8BitString(this.oct[3]);
return tmp;
}


public String getRedBinString(){
String tmp="";
for (int i=0;i<=2;i++){
tmp+=this.to8BitString(this.msk[i]&this.oct[i])+".";
}
tmp+=this.to8BitString(this.msk[3]&this.oct[3]);
return tmp;
}

public String getRedString(){
String tmp="";
for (int i=0;i<=2;i++){
tmp+=String.valueOf(this.msk[i]&this.oct[i])+".";
}
tmp+=String.valueOf(this.msk[3]&this.oct[3]);
return tmp;
}

public String getBroadcastBinString(){
String tmp="";

for (int i=0;i<=2;i++){
tmp+=this.to8BitString((255-this.msk[i])|this.oct[i])+".";
}
tmp+=this.to8BitString((255-this.msk[3])|this.oct[3]);
return tmp;
}

public String getBroadcastString(){
String tmp="";
for (int i=0;i<=2;i++){
tmp+=String.valueOf((255-this.msk[i])|this.oct[i])+".";
}
tmp+=String.valueOf((255-this.msk[3])|this.oct[3]);
return tmp;
}

public String getHostFinalString(){
String tmp="";
for (int i=0;i<=2;i++){
tmp+=String.valueOf((255-this.msk[i])|this.oct[i])+".";
}
tmp+=String.valueOf(((255-this.msk[3])|this.oct[3])-1);
return tmp;
}

public String getHostInicioString(){
String tmp="";
for (int i=0;i<=2;i++){
tmp+=String.valueOf(this.msk[i]&this.oct[i])+".";
}
tmp+=String.valueOf((this.msk[3]&this.oct[3])+1);
return tmp;
}









}
