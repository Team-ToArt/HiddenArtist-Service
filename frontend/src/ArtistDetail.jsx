import './assets/css/artist.css';
import { Link } from 'react-router-dom';

function App() {

const idx = ['1','2','3'];

  return (
    <>  

        <div style={{marginTop: '62px', marginBottom:'24px',display:'flex', flexDirection:'column', alignItems:'center'}}>
            <div style={{marginBottom:'62px'}}>
                <h2>작가 상세 페이지</h2> 
                <div>
                    <div id='profile'><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/261x261" /></div>
                    <div style={{display: 'inline-block', verticalAlign:'top', position:'relative', height:261}}>
                        <table id='artistInfo'>
                            <tbody>
                                <tr>
                                    <td>Name</td>
                                    <td>이름</td>
                                </tr>
                                <tr>
                                    <td>Summery</td>
                                    <td>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                            incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. 
                            Quis hendrerit dolor magna eget est lorem ipsum dolor sit. Volutpat odio facilisis 
                            mauris sit amet massa.</td>
                                </tr>
                                <tr>
                                    <td>Birth</td>
                                    <td>2000.01.01</td>
                                </tr>
                                <tr>
                                    <td>Genre</td>
                                    <td>장르1, 장르2, 장르3</td>
                                </tr>
                                <tr>
                                    <td>Connect</td>
                                    <td>email@email.com</td>
                                </tr>
                            </tbody>
                        </table>

                        <div style={{position:'absolute', bottom:0}}><button id='follow'>Follow</button></div>
                    </div>

                    <div style={{marginTop:'32px', marginBottom:'46px', width:1111}}>
                        <div id='descTitle'>Description</div>
                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                            incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. 
                            Quis hendrerit dolor magna eget est lorem ipsum dolor sit. Volutpat odio facilisis 
                            mauris sit amet massa. Commodo odio aenean sed adipiscing diam donec adipiscing tristique. 
                            Mi eget mauris pharetra et. Non tellus orci ac auctor augue. Elit at imperdiet dui 
                            accumsan sit. Ornare arcu dui vivamus arcu felis. Egestas integer eget aliquet nibh 
                            praesent. In hac habitasse platea dictumst quisque sagittis purus. Pulvinar elementum 
                            integer enim neque volutpat ac.</div>
                    </div>
                    
                    <div style={{width: 71, height: 4, background:'#FAFFFF', marginBottom: 32}}></div>
                    <div style={{width:1140}}>
                        <div style={{textAlign:'right', marginBottom: 16, marginRight:24}}><button id='preview3D'>3D View</button></div>
                        <div>
                            {idx.map((index) => (
                                <div class="img_art"><Link to={'/artworks/'+index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>
                        <Link to='more'>
                            <div id='moreBtn' style={{width: '100%', textAlign:'center', fontSize:24}}>More&nbsp;
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none">
                                    <path d="M19.4032 13.2H0V10.8H19.4032L10.2892 1.686L12 0L24 12L12 24L10.2892 22.314L19.4032 13.2Z" fill="white"/>
                                </svg>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default App
