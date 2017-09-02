package com.island.starpixel;
import com.island.*;
import com.island.starpixel.blocchi.*;
import java.io.*;
import java.net.*;
import java.util.*;
import android.graphics.*;
public class Generatore
{
	static void nuovo(MainActivity schermo,CharSequence nome,CharSequence seme,boolean creativa)
	{
		if(nome.length()==0)nome=schermo.lingua.nuovoUniverso;
		if(seme.length()==0)seme=String.valueOf(new Random().nextInt());
		String cartella=Memoria.casuale(schermo.cartella)+File.separator;
		Memoria.salva(cartella+schermo.blocchi.nome,nome);
		Memoria.salva(cartella+schermo.blocchi.seme,seme);
		Memoria.salva(cartella+schermo.blocchi.tempo,schermo.blocchi.zero);
		Memoria.salva(cartella+schermo.blocchi.modalita,String.valueOf(creativa));
		Memoria.salva(cartella+schermo.blocchi.fileZombie,String.valueOf(0));
		Memoria.salva(cartella+schermo.blocchi.fileMagon,String.valueOf(0));
		new Schermata(schermo,Lan.local(),cartella);
	}
	static void giocatore(Chunk c,String cartella)
	{
		giocatore(c,cartella,c.schermo.blocchi.zero,c.schermo.blocchi.zero,c.schermo.blocchi.normale,c.schermo.blocchi.Giocatore.vita());
	}
	static void giocatore(Chunk c,String cartella,String x,String y,int dimensione,double vita)
	{
		Memoria.salva(cartella+c.schermo.blocchi.chunkx,c.xvecchio);
		Memoria.salva(cartella+c.schermo.blocchi.chunky,c.yvecchio);
		Memoria.salva(cartella+c.schermo.blocchi.x,x);
		Memoria.salva(cartella+c.schermo.blocchi.y,y);
		Memoria.salva(cartella+c.schermo.blocchi.vita,String.valueOf(vita));
		Memoria.salva(cartella+c.schermo.blocchi.dimensione,String.valueOf(dimensione));
		String inv=cartella+c.schermo.blocchi.inventario+File.separator;
		for(int a=0;a<c.schermo.blocchi.slot;a++)Memoria.salva(inv+a,c.schermo.blocchi.Vuoto.nomeInglese);
	}
	static Chunk chunk(Simulatore s,int x,int y,int dimensione)
	{
		Chunk c;
		if(!Memoria.esiste(s.cartella+s.schermo.blocchi.dimensione(dimensione)+x+s.schermo.blocchi.spazio+y+File.separator))
		{
			double casuale=Math.abs(s.seme+x+y*5);
			int luce=(int)(100+casuale%155);
			int tempo=(int)(1200+casuale%12000);
			String stato=s.schermo.blocchi.statoVuoto;
			int n1=(int)(casuale%13);
			if(n1==0)stato=s.schermo.blocchi.statoNebula;
			int n2=(int)(casuale%41);
			if(n2==0&&!(x==0&&y==0))stato=s.schermo.blocchi.statoNero;
			int moltiplicatore;
			if(dimensione==s.blocchi.quasispazio)moltiplicatore=10;
			else moltiplicatore=100;
			if(x%moltiplicatore==0&&y%moltiplicatore==0&&!(x==0&&y==0))
			{
				stato=s.schermo.blocchi.statoQuasispazio;
				n2=0;
			}
			c=Chunk.crea(s,s.cartella,x,y,dimensione,tempo,luce,stato);
			if(x==0&&y==0)mondo(c,casuale);
			else if(n2!=0)
			{
				int n=(int)(casuale%100);
				if(n==0)mondo(c,casuale);
				else if(n==10)frigataUmana(c,casuale);
				else if(n==20)mondo(c,casuale);
				else if(n==30)cruiserUmano(c,casuale);
				else if(n==40)mondo(c,casuale);
				else if(n==50)frigataMagon(c,casuale);
				else if(n==60)mondo(c,casuale);
				else if(n==70)cruiserMagon(c,casuale);
				else if(n==80)mondo(c,casuale);
				else if(n==90)destroyerMagon(c,casuale);
			}
		}
		else
		{
			c=Chunk.leggi(s,s.cartella,x,y,dimensione);
		}
		return c;
	}
	private static void mondo(Chunk c,double casuale)
	{
		Blocchi b=c.schermo.blocchi;
		int raggio=(int)(5+casuale%11);
		Rect zona=null;
		if(c.s.zombie==0&&raggio==15&&!(c.x==0&&c.y==0))
		{
			int xbase=(int)(casuale%raggio-raggio/2);
			int ybase=(int)((casuale*5)%raggio-raggio/2);
			zona=new Rect(xbase,ybase,xbase+4,ybase+4);
			int n=(int)(casuale%4);
			Rect speciale;
			if(n==0)speciale=new Rect(xbase+1,ybase,xbase+3,ybase);
			else if(n==1)speciale=new Rect(xbase+1,zona.bottom,xbase+3,zona.bottom);
			else if(n==2)speciale=new Rect(xbase,ybase+1,xbase,ybase+3);
			else speciale=new Rect(zona.right,ybase+1,zona.right,ybase+3);
			int id=(int)((casuale%(b.centriSpeciale.length*3))/3);
			Blocco centro=b.centriSpeciale[id];
			Blocco terreno=b.terreniSpeciale[id];
			Blocco muro=b.muriSpeciale[id];
			Blocco porta=b.porteSpeciale[id];
			Blocco guardiano=b.guardianiSpeciale[id];
			if(guardiano==c.s.blocchi.Zombie)c.s.zombie(1);
			for(int x=xbase;x<=zona.right;x++)for(int y=ybase;y<=zona.bottom;y++)
			{
				if(x==xbase||x==zona.right||y==ybase||y==zona.bottom)
				{
					if(Oggetto.toccando(x,y,x,y,speciale.left,speciale.top,speciale.right,speciale.bottom))
					{
						entita(c,x,y,porta);
						int deltax=speciale.right-speciale.left;
						int deltay=speciale.left-speciale.right;
						if(guardiano!=null&&((deltax!=0&&x==xbase+deltax)||(deltay!=0&&y==ybase+deltay)))entita(c,x,y,guardiano);
					}
					else entita(c,x,y,muro);
				}
				if(x==xbase+2&&y==ybase+2)entita(c,x,y,centro);
				entita(c,x,y,terreno);
			}
		}
		for(int x=0;x<raggio;x++)
		{
			int y=(int)Math.sqrt(Math.pow(raggio,2)-Math.pow(x,2));
			for(int a=0;a<y;a++)
			{
				double casuale2=Math.abs(casuale*100+x*2+a*3);
				final double cambiamento=100;
				int terreno1=(int)Math.abs((casuale*100+x*(casuale2%13)+a*(casuale2%5))%(b.terreni.length*cambiamento)/cambiamento);
				int terreno2=(int)Math.abs((casuale*100+x*(casuale2%13)-a*(casuale2%5))%(b.terreni.length*cambiamento)/cambiamento);
				int terreno3=(int)Math.abs((casuale*100-x*(casuale2%13)-a*(casuale2%5))%(b.terreni.length*cambiamento)/cambiamento);
				int terreno4=(int)Math.abs((casuale*100-x*(casuale2%13)+a*(casuale2%5))%(b.terreni.length*cambiamento)/cambiamento);
				entita(c,x,a,b.terreni[terreno1],zona);
				if(a!=0)entita(c,x,-a,b.terreni[terreno2],zona);
				if(a!=0&&x!=0)entita(c,-x,-a,b.terreni[terreno3],zona);
				if(x!=0)entita(c,-x,a,b.terreni[terreno4],zona);
				Entita e=null;
				boolean ok=!(x==0&&a==0);
				if(ok&&casuale2%40<=4)
				{
					int n=(int)(casuale2%4);
					if(n==0)e=entita(c,x,a,b.piante[terreno1],zona);
					else if(n==1&&a!=0)e=entita(c,x,-a,b.piante[terreno2],zona);
					else if(n==2&&a!=0&&x!=0)e=entita(c,-x,-a,b.piante[terreno3],zona);
					else if(n==3&&x!=0)e=entita(c,-x,a,b.piante[terreno4],zona);
				}
				if(ok&&e==null&&(casuale2+7)%40<=4)
				{
					Blocco animali1=b.animali[terreno1];
					Blocco animali2=b.animali[terreno2];
					Blocco animali3=b.animali[terreno3];
					Blocco animali4=b.animali[terreno4];
					if(c.s.zombie==1)
					{
						animali1=b.Zombie;
						animali2=b.Zombie;
						animali3=b.Zombie;
						animali4=b.Zombie;
					}
					int n=(int)(casuale2%4);
					if(n==0)e=entita(c,x,a,animali1,zona);
					else if(n==1&&a!=0)e=entita(c,x,-a,animali2,zona);
					else if(n==2&&a!=0&&x!=0)e=entita(c,-x,-a,animali3,zona);
					else if(n==3&&x!=0)e=entita(c,-x,a,animali4,zona);
				}
				if(ok&&e==null&&(casuale2+13)%40<=4)
				{
					int n=(int)(casuale2%4);
					if(n==0)e=entita(c,x,a,b.acque[terreno1],zona);
					else if(n==1&&a!=0)e=entita(c,x,-a,b.acque[terreno2],zona);
					else if(n==2&&a!=0&&x!=0)e=entita(c,-x,-a,b.acque[terreno3],zona);
					else if(n==3&&x!=0)e=entita(c,-x,a,b.acque[terreno4],zona);
				}
			}
		}
	}
	private static void frigataUmana(Chunk c,double casuale)
	{
		Blocchi b=c.schermo.blocchi;
		Blocco uomo=b.Uomo;
		if(c.s.zombie==1)uomo=b.Zombie;
		for(int y=0;y<5;y++)
		{
			entita(c,0,y,b.TerrenoFerro);
			for(int x=0;x<y;x++)
			{
				if(y==3&&x==0)
				{
					entita(c,x+1,y,b.Teletrasporto);
					entita(c,-x-1,y,b.Teletrasporto);
					entita(c,x+1,y,uomo);
					entita(c,-x-1,y,uomo);
				}
				else
				{
					entita(c,x+1,y,b.TerrenoFerro);
					entita(c,-x-1,y,b.TerrenoFerro);
				}
				if(y==4||x==y-1)
				{
					if(y==2)
					{
						entita(c,x+1,y,b.Arma1);
						entita(c,-x-1,y,b.Arma1);
					}
					else if(x==0&&y==4)
					{
						entita(c,x+1,y,b.Scudo1);
						entita(c,-x-1,y,b.Scudo1);
					}
					else
					{
						entita(c,x+1,y,b.MuroFerro);
						entita(c,-x-1,y,b.MuroFerro);
					}
				}
			}
			if(y==3)
			{
				entita(c,2,y,b.Sensori);
				entita(c,-2,y,b.Rotatore);
			}
			if(y==2)entita(c,0,y,uomo);
			if(y==0)entita(c,0,y,b.MuroFerro);
			if(y==4)entita(c,0,y,b.Motore);
		}
		entita(c,0,1,b.ConsoleDiComando);
		entita(c,0,3,b.LuceElettrica);
		int n=(int)(casuale%4);
		for(int a=0;a<n;a++)Simulatore.rotea(c);
	}
	private static void cruiserUmano(Chunk c,double casuale)
	{
		Blocchi b=c.schermo.blocchi;
		Blocco uomo=b.Uomo;
		if(c.s.zombie==1)uomo=b.Zombie;
		for(int x=-4;x<=4;x++)for(int y=-4;y<=4;y++)if(!((x==4||x==-4)&&(y==4||y==-4)))
		{
			if((x==3||x==-3)&&(y==3||y==-3))
			{
				entita(c,x,y,b.Teletrasporto);
				entita(c,x,y,uomo);
			}
			else entita(c,x,y,b.TerrenoFerro);
			if(y==4)entita(c,x,y,b.Arma1);
			else if((x==4||x==-4||y==-4)&&!(y==-4&&(x==0||x==1||x==-1)))entita(c,x,y,b.MuroFerro);
		}
		for(int x=-2;x<=2;x++)for(int y=-15;y<=-5;y++)
		{
			entita(c,x,y,b.TerrenoFerro);
			if((x==2||x==-2||y==-5||y==-15)&&!(y==-5&&(x==0||x==1||x==-1)))entita(c,x,y,b.MuroFerro);
		}
		entita(c,3,-10,b.TerrenoFerro);
		entita(c,-3,-10,b.TerrenoFerro);
		entita(c,3,-10,b.Rotatore);
		entita(c,-3,-10,b.Rotatore);
		entita(c,3,-11,b.TerrenoFerro);
		entita(c,-3,-11,b.TerrenoFerro);
		entita(c,3,-11,b.MuroFerro);
		entita(c,-3,-11,b.MuroFerro);
		entita(c,3,-9,b.TerrenoFerro);
		entita(c,-3,-9,b.TerrenoFerro);
		entita(c,3,-9,b.MuroFerro);
		entita(c,-3,-9,b.MuroFerro);
		entita(c,0,2,uomo);
		entita(c,0,3,b.ConsoleDiComando);
		entita(c,0,1,b.LuceElettrica);
		entita(c,0,-2,b.LuceElettrica);
		entita(c,0,-10,b.LuceElettrica);
		entita(c,3,0,b.BancaDati);
		entita(c,-3,0,b.Sensori);
		entita(c,0,-14,b.Scudo1);
		entita(c,1,-14,b.Scudo1);
		entita(c,-1,-14,b.Scudo1);
		entita(c,1,-10,uomo);
		entita(c,-1,-10,uomo);
		for(int y=-15;y<=-6;y++)
		{
			entita(c,4,y,b.TerrenoFerro);
			entita(c,-4,y,b.TerrenoFerro);
			if(y!=-15)
			{
				entita(c,4,y,b.MuroFerro);
				entita(c,-4,y,b.MuroFerro);
			}
			else
			{
				entita(c,4,y,b.Motore);
				entita(c,-4,y,b.Motore);
			}
		}
		int n=(int)(casuale%4);
		for(int a=0;a<n;a++)Simulatore.rotea(c);
	}
	private static void destroyerUmano(Chunk c,double casuale)
	{
		Blocchi b=c.schermo.blocchi;
		
	}
	private static void frigataMagon(Chunk c,double casuale)
	{
		if(c.s.magon!=2)
		{
			Blocchi b=c.schermo.blocchi;
			Blocco magon=b.Magon;
			if(c.s.zombie==1)magon=b.Zombie;
			for(int x=-4;x<=4;x++)
			{
				entita(c,x,0,b.Pietra);
				if(x!=0&&x!=1&&x!=-1)entita(c,x,0,b.MuroPietra);
				else if(x==0)entita(c,x,0,b.Motore);
				else entita(c,x,0,b.Scudo1);
			}
			for(int x=-2;x<=2;x++)
			{
				entita(c,x,1,b.Pietra);
				entita(c,x,2,b.Pietra);
				entita(c,x,3,b.Pietra);
				if(x!=0)
				{
					if(x!=2)
					{
						if(x!=-2)entita(c,x,3,b.MuroPietra);
						else entita(c,x,3,b.Sensori);
					}
					else entita(c,x,3,b.Rotatore);
				}
				else
				{
					entita(c,x,4,b.Pietra);
					entita(c,x,4,b.ConsoleDiComando);
				}
			}
			for(int y=1;y<=5;y++)
			{
				entita(c,5,y,b.Pietra);
				entita(c,5,y,b.MuroPietra);
				if(y!=1)
				{
					entita(c,4,y,b.Pietra);
					entita(c,-4,y,b.Pietra);
				}
				else
				{
					entita(c,4,y,b.Teletrasporto);
					entita(c,-4,y,b.Teletrasporto);
				}
				entita(c,3,y,b.Pietra);
				entita(c,-3,y,b.Pietra);
				entita(c,-5,y,b.Pietra);
				entita(c,-5,y,b.MuroPietra);
			}
			for(int y=4;y<=5;y++)
			{
				entita(c,2,y,b.Pietra);
				entita(c,2,y,b.MuroPietra);
				entita(c,-2,y,b.Pietra);
				entita(c,-2,y,b.MuroPietra);
			}
			entita(c,3,1,b.LuceElettrica);
			entita(c,-3,1,b.LuceElettrica);
			entita(c,4,6,b.Pietra);
			entita(c,3,6,b.Pietra);
			entita(c,-4,6,b.Pietra);
			entita(c,-3,6,b.Pietra);
			entita(c,4,6,b.Arma1);
			entita(c,3,6,b.Arma1);
			entita(c,-4,6,b.Arma1);
			entita(c,-3,6,b.Arma1);
			entita(c,0,3,magon);
			entita(c,4,1,magon);
			entita(c,-4,1,magon);
			entita(c,4,5,magon);
			entita(c,-4,5,magon);
			entita(c,3,5,magon);
			entita(c,-3,5,magon);
			int n=(int)(casuale%4);
			for(int a=0;a<n;a++)Simulatore.rotea(c);
		}
	}
	private static void cruiserMagon(Chunk c,double casuale)
	{
		if(c.s.magon!=2)
		{
			Blocchi b=c.schermo.blocchi;
			Blocco magon=b.Magon;
			if(c.s.zombie==1)magon=b.Zombie;
			for(int x=-4;x<=4;x++)for(int y=-4;y<=4;y++)if(!((y==4||y==-4)&&(x==4||x==-4)))
					{
						if(x<=1&&x>=-1&&y<=1&&y>=-1)
						{
							entita(c,x,y,b.Pietra);
							if((x==1||x==-1)&&(y==1||y==-1))entita(c,x,y,b.MuroPietra);
						}
						else if(x==4||y==4||x==-4||y==-4)
						{
							entita(c,x,y,b.Pietra);
							entita(c,x,y,b.MuroPietra);
						}
						else
						{
							entita(c,x,y,b.Teletrasporto);
							entita(c,x,y,magon);
						}
					}
			entita(c,0,0,b.Magon);
			entita(c,0,1,b.ConsoleDiComando);
			entita(c,1,0,b.Sensori);
			entita(c,0,-1,b.Motore);
			entita(c,-1,0,b.Rotatore);
			entita(c,0,0,b.LuceElettrica);
		}
	}
	private static void destroyerMagon(Chunk c,double casuale)
	{
		if(c.s.magon==0)
		{
			c.s.magon(1);
			Blocchi b=c.schermo.blocchi;
			int d=9;
			int[]basix={d,-d,0,0,0};
			int[]basiy={0,0,d,-d,0};
			int n=4;
			int[][]murix={{0,0,n},{0,0,-n},{n,-n,0},{n,-n,0},{}};
			int[][]muriy={{n,-n,0},{n,-n,0},{0,0,n},{0,0,-n},{}};
			for(int a=0;a<basix.length;a++)
			{
				int xbase=basix[a];
				int ybase=basiy[a];
				int[]murox=murix[a];
				int[]muroy=muriy[a];
				for(int x=-4;x<=4;x++)for(int y=-4;y<=4;y++)if(!((y==4||y==-4)&&(x==4||x==-4)))
						{
							entita(c,x+xbase,y+ybase,b.Pietra);
							if(x<=1&&x>=-1&&y<=1&&y>=-1&&xbase==0&&ybase==0)
							{
								if((x==1||x==-1)&&(y==1||y==-1))entita(c,x+xbase,y+ybase,b.Arma2);
							}
							else if((x==4||y==4||x==-4||y==-4)&&(x>1||x<-1)&&(y>1||y<-1))entita(c,x+xbase,y+ybase,b.MuroPietra);
							else if(!(x==4||y==4||x==-4||y==-4))entita(c,x+xbase,y+ybase,b.Magon);
						}
				for(int index=0;index<murox.length;index++)
				{
					entita(c,murox[index]+xbase,muroy[index]+ybase,b.MuroPietra);
					if(murox[index]==0)
					{
						entita(c,murox[index]+xbase-1,muroy[index]+ybase,b.MuroPietra);
						entita(c,murox[index]+xbase+1,muroy[index]+ybase,b.MuroPietra);
					}
					if(muroy[index]==0)
					{
						entita(c,murox[index]+xbase,muroy[index]+ybase+1,b.MuroPietra);
						entita(c,murox[index]+xbase,muroy[index]+ybase-1,b.MuroPietra);
					}
				}
				if(xbase==0&&ybase==0)
				{
					entita(c,xbase,ybase,b.ReMagon);
					entita(c,xbase,ybase+1,b.ConsoleDiComando);
					entita(c,xbase+1,ybase,b.BancaDati);
					entita(c,xbase,ybase-1,b.Motore);
					entita(c,xbase-1,ybase,b.Sensori);
				}
				entita(c,xbase,ybase,b.LuceElettrica);
			}
		}
	}
	static Entita entita(Chunk c,double x,double y,Blocco b)
	{
		return Entita.crea(c,x,y,b.vita(),b);
	}
	static Entita entita(Chunk c,double x,double y,Blocco b,Rect area)
	{
		if(area==null||!Oggetto.toccandoBaseUguale(x,y,x,y,area.left,area.top,area.right,area.bottom))return entita(c,x,y,b);
		else return null;
	}
	static void controllo(final Simulatore s)
	{
		for(Entita e:s.giocatori)
		{
			Chunk su=null;
			Chunk giu=null;
			Chunk destra=null;
			Chunk sinistra=null;
			Chunk centro=null;
			for(Chunk c:s.chunk)if(c.dimensione==e.c.dimensione)
			{
				if(c.x==e.chunkX&&c.y==e.chunkY)centro=c;
				else if(c.x==e.chunkX&&c.y==e.chunkY-1)su=c;
				else if(c.x==e.chunkX-1&&c.y==e.chunkY)sinistra=c;
				else if(c.x==e.chunkX&&c.y==e.chunkY+1)giu=c;
				else if(c.x==e.chunkX+1&&c.y==e.chunkY)destra=c;
			}
			if(su==null)s.chunk=Lista.aggiungi(s.chunk,su=Generatore.chunk(s,e.chunkX,e.chunkY-1,e.c.dimensione));
			if(giu==null)s.chunk=Lista.aggiungi(s.chunk,giu=Generatore.chunk(s,e.chunkX,e.chunkY+1,e.c.dimensione));
			if(destra==null)s.chunk=Lista.aggiungi(s.chunk,destra=Generatore.chunk(s,e.chunkX+1,e.chunkY,e.c.dimensione));
			if(sinistra==null)s.chunk=Lista.aggiungi(s.chunk,sinistra=Generatore.chunk(s,e.chunkX-1,e.chunkY,e.c.dimensione));
			if(centro==null)s.chunk=Lista.aggiungi(s.chunk,centro=Generatore.chunk(s,e.chunkX,e.chunkY,e.c.dimensione));
			su.cache=Lista.aggiungi(su.cache,e.socket);
			giu.cache=Lista.aggiungi(giu.cache,e.socket);
			destra.cache=Lista.aggiungi(destra.cache,e.socket);
			sinistra.cache=Lista.aggiungi(sinistra.cache,e.socket);
			centro.cache=Lista.aggiungi(centro.cache,e.socket);
		}
		for(Chunk c:s.chunk)
		{
			if(c.cache.size()==0)
			{
				c.salva(c.s.blocchi);
				s.chunk=Lista.rimuovi(s.chunk,c);
			}
			Lista<Socket>avanzi=new Lista<Socket>(c.cache);
			Lista<Socket>fuori=new Lista<Socket>();
			for(Socket soc:c.visibili)
			{
				if(!avanzi.remove(soc))fuori=Lista.aggiungi(fuori,soc);
			}
			c.visibili=c.cache;
			c.cache=new Lista<Socket>();
			for(Socket soc:fuori)
			{
				for(Entita e:s.giocatori)if(e.socket==soc)
				{
					c.elimina(soc);
					break;
				}
			}
			for(Socket soc:avanzi)c.crea(soc);
		}
	}
}
