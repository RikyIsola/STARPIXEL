package com.island.starpixel;
import android.graphics.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.armi.*;
import com.island.starpixel.blocchi.solidi.mobili.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.solidi.scudi.*;
import com.island.starpixel.blocchi.solidi.torcia.*;
import com.island.starpixel.blocchi.strumenti.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
import com.island.starpixel.blocchi.terreni.*;
import java.io.*;
import java.net.*;
import java.util.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi.*;
import com.island.starpixel.blocchi.solidi.porte.*;
public class Entita
{
	String cartella;
	int chunkX,chunkY,id,delay;
	private int vitavecchia;
	double x,y,vita;
	String primo,secondo,terzo,quarto,quinto,sesto,settimo,ottavo;
	private String xvecchio,yvecchio,bloccoid,altrobloccoid;
	Blocco b;
	Socket socket;
	boolean creativa;
	boolean su,giu,destra,sinistra,toccato;
	Chunk c;
	Lista<Blocco>inventario=new Lista<Blocco>();
	Blocco selezionato;
	Entita evita,toccando;
	Point obiettivo;
	static int giocatoreChunkX(Blocchi b,String cartella,String ip)
	{
		return Integer.valueOf(Memoria.leggi(cartella+ip+File.separator+b.chunkx).toString());
	}
	static int giocatoreChunkY(Blocchi b,String cartella,String ip)
	{
		return Integer.valueOf(Memoria.leggi(cartella+ip+File.separator+b.chunky).toString());
	}
	static int giocatoreDimensione(Blocchi b,String cartella,String ip)
	{
		return Integer.valueOf(Memoria.leggi(cartella+ip+File.separator+b.dimensione).toString());
	}
	static Entita crea(Chunk c,double x,double y,double vita,Blocco b)
	{
		return new Entita(c,x,y,vita,b,new Lista<Blocco>(),null,false,null);
	}
	static Entita leggi(Chunk c,String file)
	{
		char divisore="&".charAt(0);
		char[]lista=file.toCharArray();
		String cache="";
		double x=0;
		double y=0;
		double vita=0;
		Blocco b=null;
		Lista<Blocco>inventario=new Lista<Blocco>();
		boolean primo=true;
		boolean secondo=true;
		boolean terzo=true;
		boolean quarto=true;
		for(int a=0;a<lista.length;a++)
		{
			if(lista[a]==divisore)
			{
				if(primo)
				{
					x=Double.valueOf(cache);
					primo=false;
				}
				else if(secondo)
				{
					y=Double.valueOf(cache);
					secondo=false;
				}
				else if(terzo)
				{
					vita=Double.valueOf(cache);
					terzo=false;
				}
				else if(quarto)
				{
					b=c.schermo.blocchi.trova(cache);
					quarto=false;
				}
				else inventario=Lista.aggiungi(inventario,c.schermo.blocchi.trova(cache));
				cache="";
			}
			else cache+=new String(new char[]{lista[a]});
		}
		return new Entita(c,x,y,vita,b,inventario,null,false,null);
	}
	public void crea(final Socket s)
	{
		Blocco blocco=blocco(s);
		final String id;
		if(blocco==c.schermo.blocchi.AltroGiocatore)
		{
			if(altrobloccoid==null)altrobloccoid=blocco.ids;
			id=altrobloccoid;
		}
		else id=bloccoid;
		c.schermo.thread().post(new Runnable()
		{
			public void run()
			{
				c.s.lan.manda(c.schermo.blocchi.creaentita,s);
				c.s.lan.manda(id,s);
				c.s.lan.manda(xvecchio,s);
				c.s.lan.manda(yvecchio,s);
				c.s.lan.manda(c.xvecchio,s);
				c.s.lan.manda(c.yvecchio,s);
			}
		});
	}
	private Entita(final Chunk c,double x,double y,double vita,Blocco b,Lista<Blocco>inventario,Socket socket,boolean creativa,String cartella)
	{
		if(b instanceof Scudi)
		{
			x=c.minX;
			y=c.minY;
		}
		this.vita=vita;
		x=Lista.arrotonda(x,2);
		y=Lista.arrotonda(y,2);
		selezionato=c.schermo.blocchi.Vuoto;
		obiettivo=new Point((int)x,(int)y);
		bloccoid=b.ids;
		if(x==(int)x)
		{
			xvecchio=String.valueOf((int)x);
			yvecchio=String.valueOf((int)y);
		}
		else
		{
			xvecchio=String.valueOf(Lista.arrotonda(x,2));
			yvecchio=String.valueOf(Lista.arrotonda(y,2));
		}
		this.c=c;
		c.entita=Lista.aggiungi(c.entita,this);
		this.socket=socket;
		this.creativa=creativa;
		this.cartella=cartella;
		chunkX=c.x;
		chunkY=c.y;
		this.b=b;
		this.x=x;
		this.y=y;
		this.inventario=inventario;
		for(Socket s:c.visibili)crea(s);
	}
	void aggiungi(Blocco oggetto)
	{
		for(int a=0;a<inventario.size();a++)if(inventario.get(a)==c.schermo.blocchi.Vuoto)
		{
			inventario.set(a,oggetto);
			break;
		}
		if(selezionato==c.schermo.blocchi.Vuoto&&!creativa)selezionato(oggetto);
	}
	void rimuovi(Blocco blocco)
	{
		boolean trovato=false;
		boolean cambiare=!creativa;
		for(int a=0;a<inventario.size();a++)
		{
			if(!trovato)
			{
				if(inventario.get(a)==blocco)trovato=true;
			}
			else
			{
				inventario.set(a-1,inventario.get(a));
				if(inventario.get(a)==selezionato)cambiare=false;
			}
		}
		inventario.set(inventario.size()-1,c.schermo.blocchi.Vuoto);
		if(cambiare)selezionato(c.schermo.blocchi.Vuoto);
	}
	void selezionato(final Blocco b)
	{
		selezionato=b;
		c.schermo.thread().post(new Runnable()
		{
			public void run()
			{
				c.s.lan.manda(c.schermo.blocchi.selezionato,socket);
				c.s.lan.manda(b.ids,socket);
			}
		});
	}
	void sempre()
	{
		if(toccando!=null&&(toccando.creativa||Oggetto.distanzaImprecisa(toccando.x,toccando.y,toccando.x+1,toccando.y+1,x,y,x+1,y+1)<=c.schermo.blocchi.distanza))
		{
			boolean salta=false;
			if(b!=c.schermo.blocchi.ConsoleDiComando&&b!=c.schermo.blocchi.BancaDati)
			{
				if(!(toccando.selezionato instanceof Solido))salta=true;
				if(!(b instanceof Terreno))salta=true;
			}
			if(delay>1||salta)
			{
				toccato=true;
				if(b instanceof Mobile&&toccando.selezionato==c.schermo.blocchi.Virus)
				{
					Entita.crea(c,x,y,1,c.schermo.blocchi.Zombie);
					vita=0;
				}
				else if(toccando.selezionato==c.schermo.blocchi.Cuore)vita=b.vita();
				vita-=(double)toccando.selezionato.forza()/10;
				if(vita<=0)toccando.aggiungi(b);
			}
			else delay++;
		}
		else
		{
			if(delay>0)delay--;
			if(vita<b.vita()&&!(b instanceof Strumento))
			{
				vita+=0.1;
				if(vita>b.vita())vita=b.vita();
			}
		}
		if(b instanceof Solido)
		{
			Solido s=(Solido)b;
			if(s instanceof Mobile)
			{
				Mobile m=(Mobile)s;
				Entita blocco=null;
				if(su&&(blocco=libero(x,y-m.velocita()))==null)y(y-m.velocita());
				if(giu&&(blocco=libero(x,y+m.velocita()))==null)y(y+m.velocita());
				if(destra&&(blocco=libero(x+m.velocita(),y))==null)x(x+m.velocita());
				if(sinistra&&(blocco=libero(x-m.velocita(),y))==null)x(x-m.velocita());
				if(m instanceof Ai)
				{
					Ai ai=(Ai)m;
					if((obiettivo.x==(int)x&&obiettivo.y==(int)y)||blocco!=null)
					{
						if(blocco!=null&&blocco!=this&&blocco.b==b&&b!=c.schermo.blocchi.Zombie)for(int a=0;a<4;a++)if(c.schermo.blocchi.random.nextInt(500)==0)
						{
							double x=this.x;
							double y=this.y;
							if(a==0)y--;
							else if(a==1)y++;
							else if(a==2)x++;
							else x--;
							Entita t=c.trovaUguale(this,x,y);
							if(t!=null&&t.b instanceof Terreno)Entita.crea(c,x,y,vita,b);
						}
						int intervalloX=c.maxX-c.minX;
						int intervalloY=c.maxY-c.minY;
						if(intervalloX>0)obiettivo.x=c.minX+c.schermo.blocchi.random.nextInt(intervalloX);
						if(intervalloY>0)obiettivo.y=c.minY+c.schermo.blocchi.random.nextInt(intervalloY);
					}
					if(ai instanceof Intelligente)
					{
						Intelligente i=(Intelligente)ai;
						boolean combattivo=i instanceof Combattivo;
						for(Entita e:c.entita)
						{
							if(!combattivo)
							{
								if(e.nemico(i.team(c.schermo.blocchi))&&c.schermo.blocchi.random.nextInt(100)==0&&delay==0&&Oggetto.distanzaImprecisa(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1)<=c.schermo.blocchi.vista/2)
								{
									Entita nuovo=Entita.crea(c,x,y,0,i.arma(c.schermo.blocchi));
									nuovo.obiettivo.x=(int)e.x;
									nuovo.obiettivo.y=(int)e.y;
									nuovo.evita=this;
									delay=20;
								}
							}
							else
							{
								if(e.nemico(i.team(c.schermo.blocchi))&&Oggetto.toccandoBaseUguale(x-0.1,y-0.1,x+1.1,y+1.1,e.x,e.y,e.x+1,e.y+1))
								{
									if(b==c.schermo.blocchi.Zombie)
									{
										e.vita=0;
										if(e.b!=c.s.blocchi.Giocatore)Entita.crea(c,e.x,e.y,1,c.schermo.blocchi.Zombie);
									}
									e.vita-=((Combattivo)b).potenza();
								}
							}
							if((e.b==c.schermo.blocchi.Teletrasporto||e.b==c.schermo.blocchi.ConsoleDiComando)&&Oggetto.toccandoBaseUguale(x-0.5,y-0.5,x+1.5,y+1.5,e.x,e.y,e.x+1,e.y+1))
							{
								if(e.b==c.schermo.blocchi.ConsoleDiComando&&e.delay==0)
								{
									e.delay=1;
									Chunk nemico=null;
									for(Chunk c:c.s.chunk)
									{
										if(c!=this.c)for(Entita e1:c.entita)if(e1.nemico(i.team(c.schermo.blocchi)))
										{
											nemico=c;
											break;
										}
										if(nemico!=null)break;
									}
									if(nemico!=null)
									{
										boolean rotatore=false;
										boolean teletrasporto=false;
										boolean armi=false;
										for(Entita e1:c.entita)
										{
											if(e1.b==c.schermo.blocchi.Teletrasporto)
											{
												try
												{
													Entita trovato=nemico.trova(null,nemico.minX+c.schermo.blocchi.random.nextInt(nemico.maxX-nemico.minX),nemico.minY+c.schermo.blocchi.random.nextInt(nemico.maxY-nemico.minY));
													if(trovato!=null&&trovato.b instanceof Terreno&&c.trova(null,e1.x,e1.y)!=e1)
													{
														if(!teletrasporto)
														{
															teletrasporto=true;
															if(c.schermo.blocchi.random.nextInt(100)==0)c.s.teletrasporta(c.x,c.y,e1.x,e1.y,nemico.x,nemico.y,trovato.x,trovato.y);
														}
													}
												}
												catch(IllegalArgumentException errore)
												{
													Lista.debug(errore);
												}
											}
											else if(e1.b==c.schermo.blocchi.Rotatore)
											{
												if(!rotatore)
												{
													rotatore=true;
													if(c.schermo.blocchi.random.nextInt(300)==0)c.s.rotea(c);
												}
											}
											else if(e1.b instanceof Arma)
											{
												if(e1.delay==0&&!armi)
												{
													armi=true;
													final int n;
													int dy=nemico.y-c.y;
													int dx=nemico.x-c.x;
													if(dy==-1)n=0;
													else if(dy==1)n=1;
													else if(dx==1)n=2;
													else n=3;
													if(b!=c.schermo.blocchi.Zombie&&c.schermo.blocchi.random.nextInt(50)==0)c.s.spara(e1,n);
												}
											}
										}
									}
								}
								obiettivo.x=(int)e.x;
								obiettivo.y=(int)e.y;
							}
						}
					}
					su=false;
					giu=false;
					destra=false;
					sinistra=false;
					if(obiettivo.x<(int)x)sinistra=true;
					else if(obiettivo.x>(int)x)destra=true;
					if(obiettivo.y<(int)y)su=true;
					else if(obiettivo.y>(int)y)giu=true;
				}
			}
			Terreno[]terreni=s.riproduzione(c.schermo.blocchi);
			if(s.riproduzione()>0)for(int a=0;a<4;a++)if(c.schermo.blocchi.random.nextInt(s.riproduzione())==0)
			{
				double x=this.x;
				double y=this.y;
				if(a==0)y--;
				else if(a==1)y++;
				else if(a==2)x++;
				else x--;
				Entita t=c.trova(null,x,y);
				if(terreni!=null&&t!=null&&t.b instanceof Terreno)for(Terreno ter:terreni)if(t.b==ter)
				{
					Entita.crea(c,x,y,vita,b);
					break;
				}
				if(terreni==null&&t!=null&&t.b instanceof Terreno)Entita.crea(c,x,y,vita,b);
			}
		}
		else if(b instanceof Strumento)
		{
			Strumento s=(Strumento)b;
			if(s instanceof Proiettile)
			{
				Proiettile p=(Proiettile)s;
				Entita blocco=null;
				if(su&&(blocco=liberoP(x,y-p.velocita()))==null)y(y-p.velocita());
				if(giu&&(blocco=liberoP(x,y+p.velocita()))==null)y(y+p.velocita());
				if(destra&&(blocco=liberoP(x+p.velocita(),y))==null)x(x+p.velocita());
				if(sinistra&&(blocco=liberoP(x-p.velocita(),y))==null)x(x-p.velocita());
				if(blocco!=null)
				{
					boolean scudi=false;
					for(Entita e:c.entita)
					{
						if(e.b instanceof Scudo&&e.vita>p.potenza())
						{
							Scudo scudo=(Scudo)e.b;
							scudi=true;
							e.vita-=p.potenza();
							Entita.crea(c,0,0,scudo.colore(c.schermo.blocchi).vita(),scudo.colore(c.schermo.blocchi));
							break;
						}
					}
					if(scudi)new Suono(c.schermo,R.raw.battle_shields_hit).start();
					else new Suono(c.schermo,R.raw.ship_hull_hit).start();
					c.esplosione(x,y,p.potenza(),2,4,scudi);
				}
				if(blocco!=null||x>c.maxX||x<c.minX||y>c.maxY||y<c.minY)rimuovi();
			}
			else if(s instanceof Lanciato)
			{
				Lanciato l=(Lanciato)s;
				Entita blocco=null;
				if(su&&(blocco=liberoS(x,y-l.velocita()))==null)y(y-l.velocita());
				if(giu&&(blocco=liberoS(x,y+l.velocita()))==null)y(y+l.velocita());
				if(destra&&(blocco=liberoS(x+l.velocita(),y))==null)x(x+l.velocita());
				if(sinistra&&(blocco=liberoS(x-l.velocita(),y))==null)x(x-l.velocita());
				if((obiettivo.x==(int)x&&obiettivo.y==(int)y)||blocco!=null)
				{
					if(blocco!=null)blocco.vita-=l.danno();
					rimuovi();
				}
				su=false;
				giu=false;
				destra=false;
				sinistra=false;
				if(obiettivo.x<(int)x)sinistra=true;
				else if(obiettivo.x>(int)x)destra=true;
				if(obiettivo.y<(int)y)su=true;
				else if(obiettivo.y>(int)y)giu=true;
			}
		}
		if(!(b instanceof Strumento))
		{
			int n;
			if(vita>b.vita()/4*3)n=0;
			else if(vita>b.vita()/4*2)n=1;
			else if(vita>b.vita()/4)n=2;
			else n=3;
			if(vitavecchia!=n)
			{
				vitavecchia=n;
				for(final Socket socket:c.visibili)
				{
					final String xvecchio=this.xvecchio;
					final String yvecchio=this.yvecchio;
					final int vitavecchia=this.vitavecchia;
					Blocco blocco=blocco(socket);
					final String id;
					if(blocco==c.schermo.blocchi.AltroGiocatore)id=altrobloccoid;
					else id=bloccoid;
					c.s.schermo.thread().post(new Runnable()
						{
							public void run()
							{
								c.s.lan.manda(c.schermo.blocchi.rottura,socket);
								c.s.lan.manda(id,socket);
								c.s.lan.manda(c.xvecchio,socket);
								c.s.lan.manda(c.yvecchio,socket);
								c.s.lan.manda(xvecchio,socket);
								c.s.lan.manda(yvecchio,socket);
								c.s.lan.manda(String.valueOf(vitavecchia),socket);
							}
						});
				}
			}
		}
		if(b==c.schermo.blocchi.Esplosione||b instanceof Scudi||b==c.schermo.blocchi.LuceTeletrasporto)vita--;
		if(vita<=0&&(!(b instanceof Strumento)||b==c.schermo.blocchi.Esplosione||b instanceof Scudi||b==c.schermo.blocchi.LuceTeletrasporto))
		{
			rimuovi();
			if(b==c.schermo.blocchi.Giocatore)
			{
				c.s.giocatori=Lista.rimuovi(c.s.giocatori,this);
				Generatore.giocatore(c,cartella);
				Entita g=Entita.giocatore(c,cartella,socket,creativa);
				g.selezionato=selezionato;
				if(!creativa)g.selezionato(c.schermo.blocchi.Vuoto);
				c.s.giocatori=Lista.aggiungi(c.s.giocatori,g);
			}
		}
	}
	static Entita giocatore(Chunk c,String cartella,Socket socket,boolean creativa)
	{
		double x=Lista.toDouble(Memoria.leggi(cartella+c.schermo.blocchi.x));
		double y=Lista.toDouble(Memoria.leggi(cartella+c.schermo.blocchi.y));
		double vita=Lista.toDouble(Memoria.leggi(cartella+c.schermo.blocchi.vita));
		String inv=cartella+c.schermo.blocchi.inventario+File.separator;
		String[]file=Memoria.file(inv);
		Lista<Blocco>inventario=new Lista<Blocco>(file.length);
		for(int a=0;a<file.length;a++)inventario.add(c.schermo.blocchi.trova(Memoria.leggi(inv+file[a])));
		return new Entita(c,x,y,vita,c.schermo.blocchi.Giocatore,inventario,socket,creativa,cartella);
	}
	void x(final double x)
	{
		final String xvecchio=this.xvecchio;
		final String yvecchio=this.yvecchio;
		final String arrivox;
		if(x==(int)x)arrivox=String.valueOf((int)x);
		else arrivox=String.valueOf(Lista.arrotonda(x,2));
		this.xvecchio=arrivox;
		for(final Socket socket:c.visibili)
		{
			Blocco blocco=blocco(socket);
			final String id;
			if(blocco==c.schermo.blocchi.AltroGiocatore)id=altrobloccoid;
			else id=bloccoid;
			c.schermo.thread().post(new Runnable()
			{
				public void run()
				{
					c.s.lan.manda(c.schermo.blocchi.x,socket);
					c.s.lan.manda(id,socket);
					c.s.lan.manda(c.xvecchio,socket);
					c.s.lan.manda(c.yvecchio,socket);
					c.s.lan.manda(xvecchio,socket);
					c.s.lan.manda(yvecchio,socket);
					c.s.lan.manda(arrivox,socket);
				}
			});
		}
		this.x=x;
	}
	void y(final double y)
	{
		final String xvecchio=this.xvecchio;
		final String yvecchio=this.yvecchio;
		final String arrivoy;
		if(y==(int)y)arrivoy=String.valueOf((int)y);
		else arrivoy=String.valueOf(Lista.arrotonda(y,2));
		this.yvecchio=arrivoy;
		for(final Socket socket:c.visibili)
		{
			Blocco blocco=blocco(socket);
			final String id;
			if(blocco==c.schermo.blocchi.AltroGiocatore)id=altrobloccoid;
			else id=bloccoid;
			c.schermo.thread().post(new Runnable()
			{
				public void run()
				{
					c.s.lan.manda(c.schermo.blocchi.y,socket);
					c.s.lan.manda(id,socket);
					c.s.lan.manda(c.xvecchio,socket);
					c.s.lan.manda(c.yvecchio,socket);
					c.s.lan.manda(xvecchio,socket);
					c.s.lan.manda(yvecchio,socket);
					c.s.lan.manda(arrivoy,socket);
				}
			});
		}
		this.y=y;
	}
	Blocco blocco(Socket s)
	{
		if(b==c.schermo.blocchi.Giocatore&&s!=socket)return c.schermo.blocchi.AltroGiocatore;
		else return b;
	}
	Entita libero(double x,double y)
	{
		Entita ritorno=c.trovaUguale(this,x,y);
		if(ritorno!=null&&(ritorno.b instanceof Terreno||ritorno.b instanceof Torcia||(ritorno.b instanceof Porta&&(b instanceof Intelligente||b==c.schermo.blocchi.Giocatore))))return null;
		else
		{
			if(ritorno==null)return this;
			else return ritorno;
		}
	}
	Entita liberoS(double x,double y)
	{
		Entita ritorno=c.trovaUguale(this,x,y);
		if(ritorno==null||ritorno.b instanceof Terreno||ritorno==evita||ritorno.b==c.schermo.blocchi.ConsoleDiComando||ritorno.b==c.schermo.blocchi.Sensori||ritorno.b instanceof Arma||ritorno.b==c.schermo.blocchi.Rotatore||ritorno.b==c.schermo.blocchi.Motore||ritorno.b==c.schermo.blocchi.BancaDati||ritorno.b instanceof Scudo||ritorno.b instanceof Torcia)return null;
		else return ritorno;
	}
	Entita liberoP(double x,double y)
	{
		Entita e=c.trova(this,x,y);
		if(e!=null&&e.b instanceof Strumento)return null;
		else return e;
	}
	String salva(Blocchi b)
	{
		if(cartella==null)
		{
			String ritorno=x+"&"+y+"&"+vita+"&"+this.b.nomeInglese+"&";
			for(Blocco bi:inventario)ritorno+=bi.nomeInglese+"&";
			return ritorno;
		}
		else
		{
			Memoria.salva(cartella+b.x,String.valueOf(x));
			Memoria.salva(cartella+b.y,String.valueOf(y));
			Memoria.salva(cartella+b.vita,String.valueOf(vita));
			String inv=cartella+b.inventario+File.separator;
			for(int a=0;a<inventario.size();a++)
			{
				Memoria.salva(inv+a,inventario.get(a).nomeInglese);
			}
			return null;
		}
	}
	void piazza(Entita toccando,final Socket socket)
	{
		if(toccando.creativa||Oggetto.distanza(toccando.x,toccando.y,toccando.x+1,toccando.y+1,x,y,x+1,y+1)<=c.schermo.blocchi.distanza)
		{
			if(toccando.selezionato instanceof Solido&&b instanceof Terreno)
			{
				Entita nuovo=Entita.crea(c,x,y,1,toccando.selezionato);
				toccando.rimuovi(toccando.selezionato);
				if(toccando.selezionato==c.schermo.blocchi.Diamante)
				{
					Lista<Entita>diamanti=new Lista<Entita>();
					for(Entita e:c.entita)if(e.b==c.schermo.blocchi.Diamante)diamanti=Lista.aggiungi(diamanti,e);
					if(diamanti.size()>=9)
					{
						Rect[]possibilita=new Rect[9];
						for(int x=0;x<3;x++)for(int y=0;y<3;y++)possibilita[x*3+y]=new Rect((int)nuovo.x-x,(int)nuovo.y-y,(int)nuovo.x+3-x,(int)nuovo.y+3-y);
						for(Rect r:possibilita)
						{
							int somma=0;
							Entita[]pezzi=new Entita[9];
							for(Entita e:diamanti)if(Oggetto.toccandoBase(e.x,e.y,e.x+1,e.y+1,r.left,r.top,r.right,r.bottom))
							{
								pezzi[somma]=e;
								somma++;
							}
							if(somma==9)
							{
								for(Entita e:pezzi)e.rimuovi();
								Entita.crea(c,nuovo.x,nuovo.y,c.schermo.blocchi.GeneratoreBuchiNeri.vita(),c.schermo.blocchi.GeneratoreBuchiNeri);
								break;
							}
						}
					}
				}
			}
			else if(b instanceof ConsoleDiComando)
			{
				c.schermo.thread().post(new Runnable()
					{
						public void run()
						{
							c.s.lan.manda(c.schermo.blocchi.comandi,socket);
						}
					});
			}
			else if(b instanceof BancaDati)
			{
				c.schermo.thread().post(new Runnable()
					{
						public void run()
						{
							c.s.lan.manda(c.schermo.blocchi.banca,socket);
							String dir=Memoria.sopra(c.cartella);
							String[]cartelle=Memoria.file(dir);
							for(int b=0;b<cartelle.length;b++)if(!Memoria.directory(dir+cartelle[b]))
								{
									String s=cartelle[b];
									//c.schermo.toast("SERVER"+s+" "+b+"/"+cartelle.length);
									boolean ok=false;
									String x="";
									String y="";
									String dimensione="";
									final char spazio=c.schermo.blocchi.spazio.charAt(0);
									for(int a=0;a<s.length();a++)
									{
										char c=s.charAt(a);
										if(c==spazio)ok=true;
										else if(!ok)
										{
											String cache=new String(new char[]{c});
											try
											{
												Integer.valueOf(cache);
												x+=cache;
											}
											catch(NumberFormatException e)
											{
												dimensione+=cache;
											}
										}
										else y+=new String(new char[]{c});
									}
									if(ok)
									{
										try
										{
											FileInputStream input=new FileInputStream(dir+s);
											String mandare="";
											while(true)
											{
												char letto=(char)input.read();
												if(letto==File.pathSeparatorChar)break;
												else mandare+=new String(new char[]{letto});
											}
											input.close();
											c.s.lan.manda(x,socket);
											c.s.lan.manda(y,socket);
											c.s.lan.manda(String.valueOf(c.s.blocchi.dimensione(dimensione)),socket);
											c.s.lan.manda(mandare,socket);
										}
										catch(IOException e)
										{
											Lista.debug(e);
										}
									}
									//c.schermo.toast(b+"/"+cartelle.length);
								}
							c.schermo.toast("fine server");
							c.s.lan.manda(c.schermo.blocchi.fine,socket);
							c.s.lan.manda(c.schermo.blocchi.fine,socket);
							c.s.lan.manda(c.schermo.blocchi.fine,socket);
							c.s.lan.manda(c.schermo.blocchi.fine,socket);
						}
					});
			}
		}
	}
	void rimuovi()
	{
		c.entita=Lista.rimuovi(c.entita,this);
		if(b==c.schermo.blocchi.ReMagon)c.s.magon(2);
		for(final Socket socket:c.visibili)
		{
			Blocco blocco=blocco(socket);
			final String id;
			if(blocco==c.schermo.blocchi.AltroGiocatore)id=altrobloccoid;
			else id=bloccoid;
			c.schermo.thread().post(new Runnable()
				{
					public void run()
					{
						c.s.lan.manda(c.schermo.blocchi.rimuovi,socket);
						c.s.lan.manda(id,socket);
						c.s.lan.manda(c.xvecchio,socket);
						c.s.lan.manda(c.yvecchio,socket);
						c.s.lan.manda(xvecchio,socket);
						c.s.lan.manda(yvecchio,socket);
					}
				});
		}
	}
	void rotazione(final int rotazione)
	{
		su=false;
		giu=false;
		destra=false;
		sinistra=false;
		if(rotazione==-90)su=true;
		else if(rotazione==0)destra=true;
		else if(rotazione==90)giu=true;
		else sinistra=true;
		for(final Socket socket:c.visibili)
		{
			Blocco blocco=blocco(socket);
			final String id;
			if(blocco==c.schermo.blocchi.AltroGiocatore)id=altrobloccoid;
			else id=bloccoid;
			c.schermo.thread().post(new Runnable()
				{
					public void run()
					{
						c.s.lan.manda(c.schermo.blocchi.rotazione,socket);
						c.s.lan.manda(id,socket);
						c.s.lan.manda(c.xvecchio,socket);
						c.s.lan.manda(c.yvecchio,socket);
						c.s.lan.manda(xvecchio,socket);
						c.s.lan.manda(yvecchio,socket);
						c.s.lan.manda(String.valueOf(rotazione),socket);
					}
				});
		}
	}
	boolean nemico(int team)
	{
		if(b instanceof Intelligente)
		{
			if(team!=((Intelligente)b).team(c.schermo.blocchi))return true;
		}
		else if(b instanceof Ai&&(team==c.schermo.blocchi.oscuri||team==c.schermo.blocchi.zombie))return true;
		else if(b==c.schermo.blocchi.Giocatore&&team!=0)return true;
		return false;
	}
}
