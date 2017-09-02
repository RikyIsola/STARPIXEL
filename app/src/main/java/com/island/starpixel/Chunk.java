package com.island.starpixel;
import com.island.*;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.mobili.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.solidi.porte.*;
import com.island.starpixel.blocchi.solidi.torcia.*;
import com.island.starpixel.blocchi.strumenti.*;
import com.island.starpixel.blocchi.terreni.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Chunk
{
	String cartella;
	MainActivity schermo;
	Lista<Entita>entita=new Lista<Entita>();
	int maxX,maxY,minX,minY,dimensione;
	String xvecchio,yvecchio;
	Lista<Socket>visibili=new Lista<Socket>();
	Lista<Socket>cache=new Lista<Socket>();
	private int tempo,luce,lucevecchia;
	String stato;
	static Chunk crea(Simulatore s,String cartella,int x,int y,int dimensione,int tempo,int luce,String stato)
	{
		cartella=cartella+s.schermo.blocchi.dimensione(dimensione)+x+s.schermo.blocchi.spazio+y;
		String file=s.schermo.blocchi.statoVuoto+File.separator+String.valueOf(tempo)+File.pathSeparator+String.valueOf(luce)+File.separator;
		Memoria.salva(cartella,file);
		return new Chunk(s,cartella,x,y,dimensione,tempo,luce,stato);
	}
	static Chunk leggi(Simulatore s,String cartella,int x,int y,int dimensione)
	{
		try
		{
			cartella=cartella+s.schermo.blocchi.dimensione(dimensione)+x+s.schermo.blocchi.spazio+y;
			//String file=Memoria.leggi(cartella);
			FileInputStream input=new FileInputStream(cartella);
			int tempo=0;
			int luce=0;
			String stato=null;
			//char[]lista=file.toCharArray();
			boolean primo=true;
			boolean secondo=true;
			boolean terzo=true;
			StringBuilder cache=new StringBuilder();
			int divisore=File.pathSeparatorChar;
			Chunk c=null;
			while(true)
			{
				int letto=input.read();
				if(letto==divisore)
				{
					if(primo)
					{
						stato=cache.toString();
						primo=false;
					}
					else if(secondo)
					{
						tempo=Lista.toInt(cache);
						secondo=false;
					}
					else if(terzo)
					{
						luce=Lista.toInt(cache);
						terzo=false;
						c=new Chunk(s,cartella,x,y,dimensione,tempo,luce,stato);
					}
					else Entita.leggi(c,cache);
					cache.setLength(0);
				}
				else if(letto==-1)break;
				else cache.append((char)letto);
			}
			if(c==null)
			{
				Memoria.cancella(cartella);
				c=Generatore.chunk(s,x,y,dimensione);
				s.schermo.toast("NULLO");
			}
			return c;
		}
		catch(IOException e)
		{
			Lista.debug(e);
			return null;
		}
	}
	public void crea(Socket socket)
	{
		s.lan.manda(s.schermo.blocchi.creachunk,socket);
		s.lan.manda(xvecchio,socket);
		s.lan.manda(yvecchio,socket);
		s.lan.manda(stato,socket);
		s.lan.manda(String.valueOf(dimensione),socket);
		for(Entita e:entita)e.crea(socket);
	}
	public void stato(final String stato)
	{
		this.stato=stato;
		schermo.thread().post(new Runnable()
		{
			public void run()
			{
				for(Socket socket:visibili)
				{
					s.lan.manda(s.blocchi.cambioStato,socket);
					s.lan.manda(xvecchio,socket);
					s.lan.manda(yvecchio,socket);
					s.lan.manda(stato,socket);
				}
			}
		});
	}
	public void elimina(Socket socket)
	{
		s.lan.manda(s.schermo.blocchi.eliminachunk,socket);
		s.lan.manda(xvecchio,socket);
		s.lan.manda(yvecchio,socket);
	}
	private Chunk(final Simulatore s,String cartella,final int x,final int y,int dimensione,int tempo,int luce,String stato)
	{
		this.stato=stato;
		this.tempo=tempo;
		this.luce=luce;
		this.dimensione=dimensione;
		this.cartella=cartella;
		this.s=s;
		schermo=s.schermo;
		this.x=x;
		this.y=y;
		xvecchio=String.valueOf(x);
		yvecchio=String.valueOf(y);
	}
	void sempre()
	{
		int luce=(int)(Math.abs((s.tempo%(double)tempo)*2/tempo-1)*this.luce);
		if(luce!=lucevecchia)
		{
			for(Socket socket:visibili)
			{
				s.lan.manda(schermo.blocchi.luceLan,socket);
				s.lan.manda(xvecchio,socket);
				s.lan.manda(yvecchio,socket);
				s.lan.manda(String.valueOf(luce),socket);
			}
			lucevecchia=luce;
		}
		if(luce==0&&schermo.blocchi.random.nextInt(15)==0&&(maxX!=0||minX!=0)&&(minY!=0||maxY!=0))
		{
			int x=schermo.blocchi.random.nextInt(maxX-minX);
			int y=schermo.blocchi.random.nextInt(maxY-minY);
			boolean ok=true;
			for(Entita e:entita)if(e.b instanceof Torcia&&Oggetto.distanza(x,y,x+1,y+1,e.x,e.y,e.x+1,e.y+1)<=5)ok=false;
			if(ok)
			{
				Entita e=trova(null,x,y);
				if(e!=null&&e.b instanceof Terreno)Entita.crea(this,e.x,e.y,schermo.blocchi.OscuroReale.vita(),schermo.blocchi.OscuroReale);
			}
		}
		minX=0;
		minY=0;
		maxX=0;
		maxY=0;
		for(Entita e:entita)
		{
			if(e.x<minX)minX=(int)e.x;
			else if(e.x>maxX)maxX=(int)e.x;
			if(e.y<minY)minY=(int)e.y;
			else if(e.y>maxY)maxY=(int)e.y;
		}
		for(Entita e:entita)e.sempre();
	}
	int x,y;
	Simulatore s;
	Entita trova(Entita evitare,double x,double y)
	{
		Entita ritorno=null;
		for(Entita e:entita)if(e!=evitare)
			{
				if(e.b instanceof Solido/*&&!(e.b instanceof Torcia)*/)
				{
					if(Oggetto.toccandoBase(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))return e;
				}
				else if(ritorno==null/*&&(e.b instanceof Terreno||e.b instanceof Torcia)*/)
				{
					if(Oggetto.toccandoBase(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))ritorno=e;
				}
			}
		return ritorno;
	}
	Entita trovaUguale(Entita evitare,double x,double y)
	{
		Entita ritorno=null;
		for(Entita e:entita)if(e!=evitare)
			{
				if(e.b instanceof Solido&&(!(e.b instanceof Torcia||e.b instanceof Porta)||(evitare!=null&&!(evitare.b instanceof Mobile||evitare.b==schermo.blocchi.Giocatore))))
				{
					if(evitare==null||!(evitare.b instanceof Giocatore&&e.b instanceof Giocatore))if(Oggetto.toccandoBaseUguale(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))return e;
				}
				else if(ritorno==null&&(e.b instanceof Terreno||e.b instanceof Torcia||e.b instanceof Porta))
				{
					if(Oggetto.toccandoBaseUguale(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))ritorno=e;
				}
			}
		return ritorno;
	}
	Entita trovaSolido(Entita evitare,double x,double y)
	{
		for(Entita e:entita)if(e!=evitare&&e.b instanceof Solido&&Oggetto.toccandoBaseUguale(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))return e;
		return null;
	}
	void salva(Blocchi b)
	{
		String stato;
		if(this.stato.equals(b.statoNebula)||this.stato.equals(b.statoNero)||this.stato.equals(b.statoQuasispazio))stato=this.stato;
		else if(entita.size()>0)stato=b.statoQualcosa;
		else stato=b.statoVuoto;
		if(stato==b.statoQualcosa||stato==b.statoVuoto)for(Entita e:entita)
		{
			if((stato==b.statoQualcosa||stato==b.statoVita)&&e.b instanceof Intelligente&&((Intelligente)e.b).team(b)==b.uomini)stato=b.statoAlleati;
			else if(stato!=b.statoGemma&&e.b==b.Diamante)stato=b.statoGemma;
			else if(stato!=b.statoZombie&&e.b==b.Zombie)stato=b.statoZombie;
			else if(stato!=b.statoBoss&&e.b instanceof Intelligente&&((Intelligente)e.b).boss())stato=b.statoBoss;
			else if(stato!=b.statoNemici&&stato!=b.statoBoss&&e.b instanceof Intelligente&&((Intelligente)e.b).team(b)!=b.uomini)stato=b.statoNemici;
			else if(stato==b.statoQualcosa&&e.b instanceof Mobile)stato=b.statoVita;
		}
		String file=stato+File.pathSeparator+String.valueOf(tempo)+File.pathSeparator+String.valueOf(luce)+File.pathSeparator;
		if(!stato.equals(b.statoNero))for(Entita e:entita)
		{
			if(!(e.b instanceof Strumento))
			{
				String salva=e.salva(b);
				if(salva!=null)file+=salva+File.pathSeparator;
			}
		}
		Memoria.salva(cartella,file);
	}
	public void esplosione(double x,double y,double danno,int riduzione,int provenienza,boolean scudi)
	{
		Entita e=trova(null,x,y);
		double adesso=danno/riduzione;
		if(e!=null&&!scudi&&!(e.b instanceof Strumento))e.vita-=Math.pow(adesso,2);
		Entita.crea(this,x,y,schermo.blocchi.Esplosione.vita(),schermo.blocchi.Esplosione);
		riduzione*=2;
		if(adesso>1&&(e==null||!(e.b instanceof Solido)))
		{
			if(provenienza!=1&&provenienza!=0&&y-1>=minY)esplosione(x,y-1,danno,riduzione,0,scudi);
			if(provenienza!=0&&provenienza!=1&&y+1<=maxY)esplosione(x,y+1,danno,riduzione,1,scudi);
			if(provenienza!=2&&provenienza!=3&&x-1>=minX)esplosione(x-1,y,danno,riduzione,3,scudi);
			if(provenienza!=3&&provenienza!=2&&x+1<=maxX)esplosione(x+1,y,danno,riduzione,2,scudi);
		}
	}
}
