package com.island.starpixel;
import android.os.*;
import android.view.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.terreni.*;
import java.net.*;
public class Gioco extends Gruppo
{
	Lan lan;
	private Simulatore simulatore;
	Lista<ChunkLan>chunk=new Lista<ChunkLan>();
	private Bottone su,giu,destra,sinistra;
	Bottone selezionato;
	EntitaLan tu;
	boolean ordine,superordine,finito,primo,connesso,teletrasportando;
	private String server;
	Schermata s;
	private String cartella;
	private Suono musica;
	private Processo processo=new Processo()
	{
		public void esegui()
		{
			loop();
		}
		public void sempre()
		{
			sempreGrafico();
		}
	}.riprendi();
	Gioco(Schermata s,Gruppo gruppo,double x,double y,double larghezza,double altezza,String server,String cartella,boolean host,boolean primo)
	{
		super(gruppo,x,y,larghezza,altezza,10,10);
		if(primo)musica=new Suono(schermo(),schermo().blocchi.pace(schermo().blocchi.normale)).start().infinito(true);
		this.primo=primo;
		this.cartella=cartella;
		this.s=s;
		this.server=server;
		if(host)simulatore=new Simulatore(schermo(),cartella);
		new Oggetto(this,0,0,1,1);
		selezionato=new Bottone(this,9,0,10,1);
		selezionato.immagine(schermo().blocchi.Vuoto.immagine()).setOnClickListener(inventario());
		su=new Bottone(this,1,7,2,8){public boolean onTouchEvent(MotionEvent e){touch(e,0);return super.onTouchEvent(e);}};
		giu=new Bottone(this,1,9,2,10){public boolean onTouchEvent(MotionEvent e){touch(e,1);return super.onTouchEvent(e);}};
		destra=new Bottone(this,2,8,3,9){public boolean onTouchEvent(MotionEvent e){touch(e,2);return super.onTouchEvent(e);}};
		sinistra=new Bottone(this,0,8,1,9){public boolean onTouchEvent(MotionEvent e){touch(e,3);return super.onTouchEvent(e);}};
		su.scrivi("<").rotazione(90).setClickable(true);
		giu.scrivi(">").rotazione(90).setClickable(true);
		destra.scrivi(">").setClickable(true);
		sinistra.scrivi("<").setClickable(true);
		if(schermo().debug()&&primo)
		{
			//new Bottone(this,4,0,6,1).setOnClickListener(ripristinosim());
			connesso=true;
		}
		else if(!primo)
		{
			new Bottone(this,4,0,6,1).setOnClickListener(collega());
		}
		lan=new Lan(schermo())
		{
			boolean inventario;
			boolean banca;
			String primo,secondo,terzo,quarto,quinto,sesto;
			public void leggi(StringBuilder messaggio,Socket socket)
			{
				if(Gioco.this.schermo()!=null)
				{
					try
					{
						if(inventario)
						{
							while(!(schermo().finestra()instanceof Inventario));
							if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.fine))
							{
								inventario=false;
								((Inventario)schermo().finestra()).finito();
							}
							else((Inventario)schermo().finestra()).aggiungi(Lista.toInt(messaggio));
						}
						else if(banca)
						{
							if(primo==null)primo=messaggio.toString();
							else if(secondo==null)secondo=messaggio.toString();
							else if(terzo==null)terzo=messaggio.toString();
							else
							{
								if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.fine))
								{
									banca=false;
									schermo().runOnUiThread(new Runnable()
										{
											public void run()
											{
												((Banca)schermo().finestra()).fine();
											}
										});
								}
								else
								{
									final int fprimo=Integer.valueOf(primo);
									final int fsecondo=Integer.valueOf(secondo);
									final int fterzo=Integer.valueOf(terzo);
									final String fmessaggio=messaggio.toString();
									schermo().runOnUiThread(new Runnable()
										{
											public void run()
											{
												((Banca)schermo().finestra()).aggiungi(fprimo,fsecondo,fterzo,fmessaggio);
											}
										});
								}
								primo=null;
								secondo=null;
								terzo=null;
							}
						}
						else if(primo==null)
						{
							if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.inventariolan))inventario=true;
							else if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.ripristino))
							{
								collega().onClick(null);
								try
								{
									Thread.sleep(1000);
								}
								catch(InterruptedException e)
								{
									Lista.debug(e);
								}
								collega().onClick(null);
							}
							else if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.banca))
							{
								schermo().runOnUiThread(new Runnable()
									{
										public void run()
										{
											new Banca(Gioco.this,lan);
										}
									});
								banca=true;
							}
							else if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.comandi))
							{
								schermo().runOnUiThread(new Runnable()
									{
										public void run()
										{
											new Comandi(Gioco.this);
										}
									});
							}
							else if(Lista.uguali(messaggio,Gioco.this.schermo().blocchi.fine))
							{
								finito=true;
								superordine=true;
							}
							else primo=messaggio.toString();
						}
						else if(secondo==null)
						{
							if(primo.equals(Gioco.this.schermo().blocchi.selezionato))
							{
								selezionato.immagine(Gioco.this.schermo().blocchi.blocchi[Lista.toInt(messaggio)].immagine());
								primo=null;
							}
							else secondo=messaggio.toString();
						}
						else if(terzo==null)
						{
							if(primo.equals(Gioco.this.schermo().blocchi.eliminachunk))
							{
								int x=Integer.valueOf(secondo);
								int y=Lista.toInt(messaggio);
								for(final ChunkLan c:chunk)if(c.x==x&&c.y==y)
									{
										for(EntitaLan e:c.entita)e.rimosso();
										schermo().runOnUiThread(new Runnable()
											{
												public void run()
												{
													Gioco.this.removeView(c);
												}
											});
										chunk=Lista.rimuovi(chunk,c);
										break;
									}
								primo=null;
								secondo=null;
							}
							else terzo=messaggio.toString();
						}
						else if(quarto==null)
						{
							if(primo.equals(Gioco.this.schermo().blocchi.cambioStato))
							{
								int x=Integer.valueOf(secondo);
								int y=Integer.valueOf(terzo);
								String stato=messaggio.toString();
								for(ChunkLan c:chunk)if(c.x==x&&c.y==y)c.stato(stato);
								primo=null;
								secondo=null;
								terzo=null;
							}
							else if(primo.equals(Gioco.this.schermo().blocchi.luceLan))
							{
								int chunkX=Integer.valueOf(secondo);
								int chunkY=Integer.valueOf(terzo);
								int luce=Lista.toInt(messaggio);
								ChunkLan trovato=null;
								for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)trovato=c;
								if(trovato!=null)trovato.luce=luce;
								primo=null;
								secondo=null;
								terzo=null;
							}
							else quarto=messaggio.toString();
						}
						else if(quinto==null)
						{
							if(primo.equals(Gioco.this.schermo().blocchi.creachunk))
							{
								int x=Integer.valueOf(secondo);
								int y=Integer.valueOf(terzo);
								String stato=quarto;
								int dimensione=Lista.toInt(messaggio);
								final ChunkLan c=new ChunkLan(Gioco.this,x,y,stato,Gioco.this,dimensione);
								chunk=Lista.aggiungi(chunk,c);
								schermo().runOnUiThread(new Runnable()
								{
									public void run()
									{
										c.setOnTouchListener(touch);
									}
								});
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
							}
							else quinto=messaggio.toString();
						}
						else if(sesto==null)
						{
							if(primo.equals(Gioco.this.schermo().blocchi.creaentita))
							{
								try
								{
									Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
									double x=Double.valueOf(terzo);
									double y=Double.valueOf(quarto);
									int chunkX=Integer.valueOf(quinto);
									int chunkY=Lista.toInt(messaggio);
									for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)new EntitaLan(c,x,y,b);
								}
								catch(NumberFormatException e)
								{
									Lista.debug(e);
								}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
							}
							else if(primo.equals(Gioco.this.schermo().blocchi.rimuovi))
							{
								try
								{
									Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
									int chunkX=Integer.valueOf(terzo);
									int chunkY=Integer.valueOf(quarto);
									double x=Double.valueOf(quinto);
									double y=Lista.toDouble(messaggio);
									for(final ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)
										{
											for(final EntitaLan e:c.entita)if(e.b==b&&e.x()==x&&e.y()==y)
												{
													try
													{
														c.entita=Lista.rimuovi(c.entita,e);
														e.rimosso();
														schermo().runOnUiThread(new Runnable()
															{
																public void run()
																{
																	c.removeView(e);
																}
															});
													}
													catch(ArrayIndexOutOfBoundsException errore)
													{
														Lista.debug(errore);
													}
													break;
												}
											break;
										}
								}
								catch(NumberFormatException e)
								{
									Lista.debug(e);
								}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
							}
							else sesto=messaggio.toString();
						}
						else
						{
							if(primo.equals(Gioco.this.schermo().blocchi.x))
							{
								try
								{
									Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
									int chunkX=Integer.valueOf(terzo);
									int chunkY=Integer.valueOf(quarto);
									double x=Double.valueOf(quinto);
									double y=Double.valueOf(sesto);
									double arrivo=Lista.toDouble(messaggio);
									arrivo=Double.valueOf(messaggio.toString());
									for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)
										{
											for(EntitaLan e:c.entita)if(e.b==b&&e.x()==x&&e.y()==y)
												{
													e.x(arrivo);
													break;
												}
											break;
										}
								}
								catch(NumberFormatException e)
								{
									Lista.debug(e);
								}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
								sesto=null;
							}
							else if(primo.equals(Gioco.this.schermo().blocchi.rottura))
							{
								Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
								int chunkX=Integer.valueOf(terzo);
								int chunkY=Integer.valueOf(quarto);
								double x=Double.valueOf(quinto);
								double y=Double.valueOf(sesto);
								int arrivo=Lista.toInt(messaggio);
								for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)
									{
										for(EntitaLan e:c.entita)if(e.b==b&&e.x()==x&&e.y()==y)
											{
												e.rottura(arrivo);
												break;
											}
										break;
									}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
								sesto=null;
							}
							else if(primo.equals(Gioco.this.schermo().blocchi.y))
							{
								try
								{
									Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
									int chunkX=Integer.valueOf(terzo);
									int chunkY=Integer.valueOf(quarto);
									double x=Double.valueOf(quinto);
									double y=Double.valueOf(sesto);
									double arrivo=Lista.toDouble(messaggio);
									arrivo=Double.valueOf(messaggio.toString());
									for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)
										{
											for(EntitaLan e:c.entita)if(e.b==b&&e.x()==x&&e.y()==y)
												{
													e.y(arrivo);
													break;
												}
											break;
										}
								}
								catch(NumberFormatException e)
								{
									Lista.debug(e);
								}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
								sesto=null;
							}
							else if(primo.equals(Gioco.this.schermo().blocchi.rotazione))
							{
								Blocco b=Gioco.this.schermo().blocchi.blocchi[Integer.valueOf(secondo)];
								int chunkX=Integer.valueOf(terzo);
								int chunkY=Integer.valueOf(quarto);
								double x=Double.valueOf(quinto);
								double y=Double.valueOf(sesto);
								int arrivo=Lista.toInt(messaggio);
								for(ChunkLan c:chunk)if(c.x==chunkX&&c.y==chunkY)
									{
										for(EntitaLan e:c.entita)if(e.b==b&&e.x()==x&&e.y()==y)
											{
												e.rotazione(arrivo);
												break;
											}
										break;
									}
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
								sesto=null;
							}
							else
							{
								primo=null;
								secondo=null;
								terzo=null;
								quarto=null;
								quinto=null;
								sesto=null;
								schermo().toast("ERRORE GIOCO");
							}
						}
					}
					catch(NullPointerException e)
					{
						Lista.debug(e);
					}
				}
			}
		};
		if(primo)
		{
			lan.inizioClient(server,30001);
			lan.manda(Gioco.this.schermo().blocchi.crea,true);
		}
	}
	private View.OnClickListener collega()
	{
		return new View.OnClickListener()
		{
			public void onClick(View v)
			{
				connesso=!connesso;
				final boolean con=connesso;
				new Processo(schermo())
				{
					public void esegui()
					{
						if(schermo().finestra() instanceof Comandi||schermo().finestra() instanceof Inventario||schermo().finestra() instanceof Banca)schermo().finestra().cancel();
						if(con)
						{
							lan.inizioClient(server,30001);
							try
							{
								Thread.sleep(10);
							}
							catch(InterruptedException e)
							{
								Lista.debug(e);
							}
							lan.manda(Gioco.this.schermo().blocchi.crea,true);
						}
						else
						{
							schermo().runOnUiThread(new Runnable()
							{
								public void run()
								{
									tu=null;
									for(ChunkLan c:chunk)removeView(c);
									chunk=new Lista<ChunkLan>();
									selezionato.immagine(R.drawable.vuoto);
								}
							});
							lan.fine();
						}
					}
				};
			}
		};
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void sempre()
	{
		if(simulatore!=null)simulatore.sempre();
	}
	public void sempreGrafico()
	{
		if(musica!=null)
		{
			boolean esci=false;
			for(ChunkLan c:chunk)
			{
				for(EntitaLan e:c.entita)
				{
					if(e.b instanceof Intelligente)
					{
						Intelligente i=(Intelligente)e.b;
						if(i.team(schermo().blocchi)!=0)
						{
							esci=true;
							if(!i.boss())
							{
								if(schermo().blocchi.pace(musica.suono(),e.chunk.dimensione)||schermo().blocchi.pace(musica.suono(),schermo().blocchi.normale))
								{
									musica.rilascia();
									musica=new Suono(schermo(),Blocchi.battaglia()).start().infinito(true);
								}
							}
							else
							{
								if(schermo().blocchi.pace(musica.suono(),e.chunk.dimensione)||Blocchi.battaglia(musica.suono()))
								{
									musica.rilascia();
									musica=new Suono(schermo(),Blocchi.boss()).start().infinito(true);
									break;
								}
							}
						}
					}
				}
				if(esci)break;
			}
			int dimensione;
			if(tu!=null)dimensione=tu.chunk.dimensione;
			else dimensione=schermo().blocchi.normale;
			if(!esci&&(Blocchi.battaglia(musica.suono())||Blocchi.boss(musica.suono())||!schermo().blocchi.pace(musica.suono(),dimensione)))
			{
				musica.rilascia();
				musica=new Suono(schermo(),schermo().blocchi.pace(dimensione)).start().infinito(true);
			}
		}
		
		if(s.libero()&&tu!=null)
		{
			//tu.chunk.sempre();
			for(EntitaLan e:tu.chunk.entita)e.sempre();
		}
		if(s.libero())
		{
			if(tu!=null)tu.chunk.sempre();
			if(superordine&&tu!=null)
			{
				schermo().runOnUiThread(superordiner);
				superordine=false;
			}
			if(ordine&&tu!=null)
			{
				try
				{
					schermo().runOnUiThread(ordiner);
				}
				catch(NullPointerException e)
				{
					Lista.debug(e);
				}
				ordine=false;
			}
			if(tu!=null)
			{
				tu.chunk.trans(tu.x()-schermo().blocchi.vista/2,tu.y()-schermo().blocchi.vista/2);
			}
		}
	}
	public void onStop()
	{
		if(simulatore!=null)simulatore.stop();
		processo.chiudi();
		if(musica!=null)musica.rilascia();
		lan.fine();
	}
	public void pausa()
	{
		if(simulatore!=null)simulatore.pausa();
		processo.pausa();
		lan.pausa();
	}
	public void riprendi()
	{
		if(simulatore!=null)simulatore.riprendi();
		processo.riprendi();
		lan.riprendi();
	}
	private void touch(MotionEvent e,int n)
	{
		if(e.getAction()==MotionEvent.ACTION_DOWN)
		{
			lan.manda(Gioco.this.schermo().blocchi.giu);
			lan.manda(String.valueOf(n));
		}
		else if(e.getAction()==MotionEvent.ACTION_UP)
		{
			lan.manda(Gioco.this.schermo().blocchi.su);
			lan.manda(String.valueOf(n));
		}
	}
	View.OnTouchListener touch=new View.OnTouchListener()
	{
		public boolean onTouch(View p1,MotionEvent p2)
		{
			if(p2.getAction()==MotionEvent.ACTION_DOWN)
			{
				Gruppo g=(Gruppo)p1;
				double x=p2.getX()/g.unitaX()+g.transX();
				double y=p2.getY()/g.unitaY()+g.transY();
				lan.manda(Gioco.this.schermo().blocchi.iniziotocco);
				lan.manda(String.valueOf(Lista.arrotonda(x,2)));
				lan.manda(String.valueOf(Lista.arrotonda(y,2)));
			}
			else if(p2.getAction()==MotionEvent.ACTION_UP)lan.manda(Gioco.this.schermo().blocchi.finetocco);
			return true;
		}
	};
	private View.OnClickListener inventario()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(finito)new Inventario(schermo(),lan,(Bottone)p1);
			}
		};
	}
	void ripristino()
	{
		if(simulatore!=null)simulatore.ripristino();
	}
	void salva(Bundle b)
	{
		if(simulatore!=null)simulatore.salva(b);
	}
	void leggi(Bundle b)
	{
		if(simulatore!=null)simulatore.leggi(b);
	}
	View.OnClickListener ripristinosim()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				simulatore.ripristino();
				simulatore.stop();
				simulatore=new Simulatore(schermo(),cartella);
			}
		};
	}
	private Runnable superordiner=new Runnable()
	{
		public void run()
		{
			for(ChunkLan c:chunk)c.setVisibility(View.INVISIBLE);
			tu.chunk.setVisibility(View.VISIBLE);
			su.bringToFront();
			giu.bringToFront();
			destra.bringToFront();
			sinistra.bringToFront();
		}
	};
	private Runnable ordiner=new Runnable()
	{
		public void run()
		{
			for(int a=0;a<tu.chunk.getChildCount();a++)
			{
				View v=tu.chunk.getChildAt(a);
				if(v instanceof EntitaLan)
				{
					if(!(((EntitaLan)v).b instanceof Terreno))v.bringToFront();
				}
			}
		}
	};
}
