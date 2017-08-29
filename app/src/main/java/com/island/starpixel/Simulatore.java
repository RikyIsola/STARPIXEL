package com.island.starpixel;
import android.os.*;
import com.island.*;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.armi.*;
import com.island.starpixel.blocchi.strumenti.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
import com.island.starpixel.blocchi.terreni.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class Simulatore
{
	Lista<Chunk>chunk=new Lista<Chunk>();
	Lista<Entita>giocatori=new Lista<Entita>();
	private Lista<Entita>tocchi=new Lista<Entita>();
	Lan lan;
	double seme;
	MainActivity schermo;
	String cartella;
	private boolean fine,creativa;
	long tempo;
	Blocchi blocchi;
	int zombie,magon;
	void zombie(final int zombie)
	{
		schermo.thread().post(new Runnable()
		{
			public void run()
			{
				Simulatore.this.zombie=zombie;
				Memoria.salva(cartella+Simulatore.this.schermo.blocchi.fileZombie,String.valueOf(zombie));
			}
		});
	}
	void magon(final int magon)
	{
		schermo.thread().post(new Runnable()
			{
				public void run()
				{
					Simulatore.this.magon=magon;
					Memoria.salva(cartella+Simulatore.this.schermo.blocchi.fileMagon,String.valueOf(magon));
				}
			});
	}
	Simulatore(final Schermo schermo,final String cartella)
	{
		this.schermo=(MainActivity)schermo;
		blocchi=this.schermo.blocchi;
		this.cartella=cartella;
		tempo=Lista.toLong(Memoria.leggi(cartella+Simulatore.this.schermo.blocchi.tempo));
		seme=Lista.toDouble(Memoria.leggi(cartella+Simulatore.this.schermo.blocchi.seme));
		creativa=Lista.uguali(Memoria.leggi(cartella+Simulatore.this.schermo.blocchi.modalita),Lista.vero);
		zombie=Lista.toInt(Memoria.leggi(cartella+Simulatore.this.schermo.blocchi.fileZombie));
		magon=Lista.toInt(Memoria.leggi(cartella+Simulatore.this.schermo.blocchi.fileMagon));
		lan=new Lan(schermo)
		{
			public void leggi(final StringBuilder mess,final Socket socket)
			{
				try
				{
					String messaggio=mess.toString();
					Entita g=null;
					int id=-1;
					for(int a=0;a<giocatori.size();a++)if(giocatori.get(a).socket==socket)
						{
							g=giocatori.get(id=a);
							break;
						}
					if(messaggio.equals(Simulatore.this.schermo.blocchi.crea))
					{
						schermo().thread().post(new Runnable()
							{
								public void run()
								{
									boolean aggiungi=true;
									Chunk c=null;
									String mac=Lan.ip(socket);
									String car=cartella+mac;
									if(!Memoria.esiste(car))
									{
										for(Chunk ch:chunk)if(ch.x==0&&ch.y==0)
											{
												c=ch;
												aggiungi=false;
											}
										if(c==null)c=Generatore.chunk(Simulatore.this,0,0/*99,99*/,Simulatore.this.schermo.blocchi.normale);
										Generatore.giocatore(c,car+File.separator);
									}
									int chunkX=Entita.giocatoreChunkX(Simulatore.this.schermo.blocchi,cartella,mac);
									int chunkY=Entita.giocatoreChunkY(Simulatore.this.schermo.blocchi,cartella,mac);
									int dimensione=Entita.giocatoreDimensione(Simulatore.this.schermo.blocchi,cartella,mac);
									for(Chunk ch:chunk)if(ch.x==chunkX&&ch.y==chunkY)
										{
											c=ch;
											aggiungi=false;
										}
									if(c==null)c=Generatore.chunk(Simulatore.this,chunkX,chunkY,dimensione);
									Entita e=Entita.giocatore(c,car+File.separator,socket,creativa);
									giocatori=Lista.aggiungi(giocatori,e);
									tocchi=Lista.aggiungi(tocchi,e);
									tocchi.set(tocchi.size()-1,null);
									if(aggiungi)chunk=Lista.aggiungi(chunk,c);
									Generatore.controllo(Simulatore.this);
									manda(Simulatore.this.schermo.blocchi.fine,socket);
								}
							});
					}
					else if(g.primo==null)
					{
						if(messaggio.equals(Simulatore.this.schermo.blocchi.inventariolan))
						{
							final Entita fg=g;
							schermo().thread().post(new Runnable()
								{
									public void run()
									{
										manda(Simulatore.this.schermo.blocchi.inventariolan,socket);
										if(fg.creativa)for(Blocco b:Simulatore.this.schermo.blocchi.blocchi)manda(b.ids,socket);
										else for(Blocco b:fg.inventario)manda(b.ids,socket);
										manda(Simulatore.this.schermo.blocchi.fine,socket);
									}
								});
						}
						else if(messaggio.equals(Simulatore.this.schermo.blocchi.rotazione))
						{
							rotea(g.c);
						}
						else if(messaggio.equals(Simulatore.this.schermo.blocchi.finetocco))
						{
							if(tocchi.get(id)!=null)
							{
								Entita b=tocchi.get(id).toccando;
								tocchi.get(id).toccando=null;
								if(!tocchi.get(id).toccato)tocchi.get(id).piazza(b,socket);
								tocchi.get(id).toccato=false;
								tocchi.set(id,null);
							}
						}
						else g.primo=messaggio.toString();
					}
					else if(g.secondo==null)
					{
						if(g.primo.equals(Simulatore.this.schermo.blocchi.motori))
						{
							final int n=Integer.valueOf(messaggio.toString());
							final Entita fg=g;
							schermo().thread().post(new Runnable()
								{
									public void run()
									{
										int x=fg.chunkX;
										int y=fg.chunkY;
										if(n==0)y--;
										else if(n==1)y++;
										else if(n==2)x++;
										else x--;
										Chunk arrivo=null;
										for(Chunk c:chunk)if(c.x==x&&c.y==y)arrivo=c;
										if(arrivo.stato.equals(blocchi.statoNero))
										{
											boolean ok=true;
											while(ok)
											{
												arrivo=Generatore.chunk(Simulatore.this,blocchi.random.nextInt(10000)-5000,blocchi.random.nextInt(10000)-5000,arrivo.dimensione);
												ok=false;
												for(Chunk c:chunk)if(arrivo==c)ok=true;
												if(arrivo.stato.equals(blocchi.statoNero))ok=true;
											}
											for(Entita e:arrivo.entita)if(e.b!=Simulatore.this.schermo.blocchi.Giocatore)e.rimuovi();
											chunk=Lista.aggiungi(chunk,arrivo);
										}
										else if(arrivo.stato.equals(blocchi.statoQuasispazio))
										{
											int moltiplicatore;
											int dimensione;
											if(arrivo.dimensione==blocchi.quasispazio)
											{
												moltiplicatore=100;
												dimensione=blocchi.normale;
											}
											else
											{
												moltiplicatore=1;
												dimensione=blocchi.quasispazio;
											}
											int arrivoX=arrivo.x*moltiplicatore/10;
											int arrivoY=arrivo.y*moltiplicatore/10;
											if(n==0)arrivoY++;
											else if(n==1)arrivoY--;
											else if(n==2)arrivoX--;
											else arrivoX++;
											arrivo=Generatore.chunk(Simulatore.this,arrivoX,arrivoY,dimensione);
											for(Entita e:arrivo.entita)if(e.b!=Simulatore.this.schermo.blocchi.Giocatore)e.rimuovi();
											chunk=Lista.aggiungi(chunk,arrivo);
											schermo().toast(arrivo.x+" "+arrivo.y+" "+arrivo.dimensione+" "+arrivo.stato);
										}
										if(arrivo.entita.size()==0)
										{
											final Chunk farrivo=arrivo;
											schermo().thread().post(new Runnable()
												{
													public void run()
													{
														for(Entita e:fg.c.entita)
														{
															if(!(e.b instanceof Strumento))
															{
																e.rimuovi();
																Entita giocatore;
																if(e.b==Simulatore.this.schermo.blocchi.Giocatore)
																{
																	giocatori=Lista.rimuovi(giocatori,e);
																	Generatore.giocatore(farrivo,e.cartella,String.valueOf(Lista.arrotonda(e.x,2)),String.valueOf(Lista.arrotonda(e.y,2)),farrivo.dimensione,Lista.arrotonda(e.vita,2));
																	giocatore=Entita.giocatore(farrivo,e.cartella,e.socket,e.creativa);
																	giocatore.selezionato=e.selezionato;
																	giocatori=Lista.aggiungi(giocatori,giocatore);
																}
																else giocatore=Entita.crea(farrivo,e.x,e.y,e.vita,e.b);
																giocatore.inventario=e.inventario;
															}
														}
														Generatore.controllo(Simulatore.this);
													}
												});
										}
									}
								});
							g.primo=null;
						}
						else if(g.primo.equals(Simulatore.this.schermo.blocchi.giu))
						{
							int n=Integer.valueOf(messaggio.toString());
							if(n==0)g.su=true;
							else if(n==1)g.giu=true;
							else if(n==2)g.destra=true;
							else g.sinistra=true;
							g.primo=null;
						}
						else if(g.primo.equals(Simulatore.this.schermo.blocchi.su))
						{
							int n=Integer.valueOf(messaggio.toString());
							if(n==0)g.su=false;
							else if(n==1)g.giu=false;
							else if(n==2)g.destra=false;
							else g.sinistra=false;
							g.primo=null;
						}
						else if(g.primo.equals(Simulatore.this.schermo.blocchi.selezionato))
						{
							if(creativa)g.selezionato=Simulatore.this.schermo.blocchi.blocchi[Integer.valueOf(messaggio.toString())];
							else g.selezionato=g.inventario.get(Integer.valueOf(messaggio.toString()));
							g.primo=null;
						}
						else g.secondo=messaggio.toString();
					}
					else if(g.terzo==null)
					{
						if(g.primo.equals(Simulatore.this.schermo.blocchi.iniziotocco))
						{
							double x=Double.valueOf(g.secondo);
							double y=Double.valueOf(messaggio.toString());
							Entita trovato=null;
							for(Entita e:g.c.entita)if(Oggetto.toccandoBaseUguale(x,y,x,y,e.x,e.y,e.x+1,e.y+1))
								{
									trovato=e;
									if(trovato.b instanceof Solido)break;
								}
							if(g.selezionato instanceof Lanciato)
							{
								if(g.delay==0)
								{
									Entita e=Entita.crea(g.c,g.x,g.y,0,g.selezionato);
									if(e.x>x)e.rotazione(180);
									e.obiettivo.x=(int)x;
									e.obiettivo.y=(int)y;
									e.evita=g;
									g.delay=20;
								}
							}
							else if(trovato!=null)
							{
								tocchi.set(id,trovato);
								trovato.toccando=g;
							}
							else
							{
								if(g.selezionato instanceof Terreno)
								{
									if(y<0)y--;
									if(x<0)x--;
									Entita.crea(g.c,(int)x,(int)y,1,g.selezionato);
									g.rimuovi(g.selezionato);
								}
							}
							g.primo=null;
							g.secondo=null;
						}
						else if(g.primo.equals(Simulatore.this.schermo.blocchi.creazione))
						{
							g.rimuovi(Simulatore.this.schermo.blocchi.blocchi[Integer.valueOf(g.secondo)]);
							g.aggiungi(Simulatore.this.schermo.blocchi.blocchi[Integer.valueOf(messaggio.toString())]);
							g.primo=null;
							g.secondo=null;
						}
						else g.terzo=messaggio.toString();
					}
					else if(g.quarto==null)
					{
						if(g.primo.equals(Simulatore.this.schermo.blocchi.armi))
						{
							final String fsecondo=g.secondo;
							final String fterzo=g.terzo;
							final String fmessaggio=messaggio.toString();
							final Entita fg=g;
							schermo().thread().post(new Runnable()
								{
									public void run()
									{
										double x=Double.valueOf(fsecondo);
										double y=Double.valueOf(fterzo);
										int n=Integer.valueOf(fmessaggio);
										Entita trovato=fg.c.trova(null,x,y);
										if(trovato!=null)spara(trovato,n);
									}
								});
							g.primo=null;
							g.secondo=null;
							g.terzo=null;
						}
						else g.quarto=messaggio.toString();
					}
					else if(g.quinto==null)
					{
						g.quinto=messaggio.toString();
					}
					else if(g.sesto==null)
					{
						g.sesto=messaggio.toString();
					}
					else if(g.settimo==null)
					{
						g.settimo=messaggio.toString();
					}
					else if(g.ottavo==null)
					{
						g.ottavo=messaggio.toString();
					}
					else
					{
						if(g.primo.equals(Simulatore.this.schermo.blocchi.teletrasporta))
						{
							teletrasporta(Integer.valueOf(g.secondo),Integer.valueOf(g.terzo),Double.valueOf(g.quarto),Double.valueOf(g.quinto),Integer.valueOf(g.sesto),Integer.valueOf(g.settimo),Double.valueOf(g.ottavo),Double.valueOf(messaggio.toString()));
							g.primo=null;
							g.secondo=null;
							g.terzo=null;
							g.quarto=null;
							g.quinto=null;
							g.sesto=null;
							g.settimo=null;
							g.ottavo=null;
						}
					}
				}
				catch(NullPointerException e)
				{
					Lista.debug(e);
				}
			}
			public void uscito(final Socket socket)
			{
				schermo().thread().post(new Runnable()
				{
					public void run()
					{
						Entita g=null;
						for(Entita e:giocatori)if(e.socket==socket)
							{
								g=e;
								break;
							}
						if(g!=null)
						{
							giocatori=Lista.rimuovi(giocatori,g);
							Generatore.controllo(Simulatore.this);
							g.salva(Simulatore.this.schermo.blocchi);
							g.rimuovi();
						}
					}
				});
			}
		};
		lan.inizioServer(30001,100);
		try
		{
			Thread.sleep(10);
		}
		catch(InterruptedException e)
		{
			Lista.debug(e);
		}
	}
	public void sempre()
	{
		tempo++;
		if(!fine)for(Chunk c:chunk)c.sempre();
	}
	public void stop()
	{
		fine=true;
		for(Chunk c:chunk)c.salva(blocchi);
		lan.chiudiServer();
		lan.fine();
	}
	public void pausa()
	{
		lan.pausa();
		for(Chunk c:chunk)c.salva(blocchi);
	}
	public void riprendi()
	{
		lan.riprendi();
	}
	void teletrasporta(int chunkX,int chunkY,double x,double y,int arrivoChunkX,int arrivoChunkY,double arrivoX,double arrivoY)
	{
		Chunk arrivo=null;
		Chunk partenza=null;
		for(Chunk c:chunk)
		{
			if(c.x==chunkX&&c.y==chunkY)partenza=c;
			if(c.x==arrivoChunkX&&c.y==arrivoChunkY)arrivo=c;
		}
		Entita partente=null;
		for(Entita e:partenza.entita)if(e.b instanceof Solido&&Oggetto.toccandoBase(e.x,e.y,e.x+1,e.y+1,x,y,x+1,y+1))partente=e;
		if(arrivo.stato.equals(schermo.blocchi.statoNero))partente=null;
		if(partente!=null)
		{
			if(arrivo==partenza)
			{
				partente.x(arrivoX);
				partente.y(arrivoY);
			}
			else
			{
				partente.rimuovi();
				Entita giocatore;
				if(partente.b==Simulatore.this.schermo.blocchi.Giocatore)
				{
					giocatori=Lista.rimuovi(giocatori,partente);
					Generatore.giocatore(arrivo,partente.cartella,String.valueOf(arrivoX),String.valueOf(arrivoY),partente.c.dimensione,partente.vita);
					giocatore=Entita.giocatore(arrivo,partente.cartella,partente.socket,partente.creativa);
					giocatore.selezionato=partente.selezionato;
					giocatori=Lista.aggiungi(giocatori,giocatore);
					schermo.thread().post(new Runnable()
						{
							public void run()
							{
								Generatore.controllo(Simulatore.this);
							}
						});
				}
				else giocatore=Entita.crea(arrivo,arrivoX,arrivoY,partente.vita,partente.b);
				giocatore.inventario=partente.inventario;
			}
			Entita.crea(partenza,x,y,schermo.blocchi.LuceTeletrasporto.vita(),schermo.blocchi.LuceTeletrasporto);
			Entita.crea(arrivo,arrivoX,arrivoY,schermo.blocchi.LuceTeletrasporto.vita(),schermo.blocchi.LuceTeletrasporto);
		}
	}
	static void rotea(Chunk c)
	{
		for(Entita o:c.entita)if(!(o.b instanceof Strumento))
		{
			double x=o.x;
			double y=o.y;
			if(x>0&&y>0)
			{
				double cache=x;
				x=y;
				y=cache;
				y*=-1;
			}
			else if(x>0&&y<0)
			{
				double cache=x;
				x=y;
				y=cache;
				y*=-1;
			}
			else if(x<0&&y>0)
			{
				double cache=x;
				x=y;
				y=cache;
				y*=-1;
			}
			else if(x<0&&y<0)
			{
				double cache=x;
				x=y;
				y=cache;
				y*=-1;
			}
			else if(x==0&&y>0)
			{
				x=y;
				y=0;
			}
			else if(x==0&&y<0)
			{
				x=y;
				y=0;
			}
			else if(x>0&&y==0)
			{
				y=-x;
				x=0;
			}
			else if(x<0&&y==0)
			{
				y=-x;
				x=0;
			}
			o.x(x);
			o.y(y);
		}
	}
	void spara(Entita trovato,int n)
	{
		if(trovato.delay==0)
		{
			double x=trovato.x;
			double y=trovato.y;
			int chunkX=trovato.chunkX;
			int chunkY=trovato.chunkY;
			int rotazione;
			if(n==0)
			{
				chunkY--;
				rotazione=-90;
			}
			else if(n==1)
			{
				chunkY++;
				rotazione=90;
			}
			else if(n==2)
			{
				chunkX++;
				rotazione=0;
			}
			else
			{
				chunkX--;
				rotazione=180;
			}
			Chunk arrivo=null;
			for(Chunk c:chunk)if(c.x==chunkX&&c.y==chunkY)arrivo=c;
			if(arrivo!=null)
			{
				if(n==0)y=arrivo.maxY;
				else if(n==1)y=arrivo.minY;
				else if(n==2)x=arrivo.minX;
				else x=arrivo.maxX;
				if(trovato!=null&&trovato.b instanceof Arma)
				{
					if(trovato.b==schermo.blocchi.GeneratoreBuchiNeri)
					{
						arrivo.stato(schermo.blocchi.statoNero);
						for(Entita e:arrivo.entita)
						{
							e.rimuovi();
							if(e.b==schermo.blocchi.Giocatore)
							{
								giocatori=Lista.rimuovi(giocatori,e);
								Generatore.giocatore(trovato.c,e.cartella,blocchi.zero,blocchi.zero,e.c.dimensione,e.b.vita());
								Entita giocatore=Entita.giocatore(trovato.c,e.cartella,e.socket,e.creativa);
								giocatore.selezionato=e.selezionato;
								giocatori=Lista.aggiungi(giocatori,giocatore);
								schermo.thread().post(new Runnable()
									{
										public void run()
										{
											Generatore.controllo(Simulatore.this);
										}
									});
							}
						}
					}
					else Entita.crea(arrivo,x,y,0,((Arma)trovato.b).siluro(schermo.blocchi)).rotazione(rotazione);
				}
				trovato.delay=20;
			}
		}
	}
	void ripristino()
	{
		for(int a=0;a<lan.connessi();a++)lan.manda(blocchi.ripristino,lan.connesso(a));
	}
	void salva(Bundle b)
	{
	}
	void leggi(Bundle b)
	{
	}
}
