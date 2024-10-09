import './assets/css/artworks.css';
import { Link } from 'react-router-dom';

function App() {

const idx = ['1','2','3'];

  return (
    <>  

        <div style={{marginTop: '62px', marginBottom:'24px',display:'flex', flexDirection:'column', alignItems:'center'}}>
            <div style={{marginBottom:'62px'}}>
                <h2>작품 상세 페이지</h2> 
                <div>
                    <div id='artworkImg'><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/546x546" /></div>
                    <div style={{display: 'inline-block', verticalAlign:'top', position:'relative', height:261}}>
                        <table id='artworksInfo'>
                            <tbody>
                                <tr>
                                    <td>Title</td>
                                    <td>작품명</td>
                                </tr>
                                <tr>
                                    <td>Author</td>
                                    <td>작가명</td>
                                </tr>
                                <tr>
                                    <td>Genre</td>
                                    <td>장르1</td>
                                </tr>
                                <tr>
                                    <td>Media</td>
                                    <td>매체</td>
                                </tr>
                                <tr>
                                    <td>Size</td>
                                    <td>사이즈</td>
                                </tr>
                                <tr>
                                    <td>Description</td>
                                    <td>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                                        incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. 
                                        Quis hendrerit dolor magna eget est lorem ipsum dolor sit. Volutpat odio facilisis 
                                        mauris sit amet massa. Commodo odio aenean sed adipiscing diam donec adipiscing tristique. 
                                        Mi eget mauris pharetra et. Non tellus orci ac auctor augue. Elit at imperdiet dui 
                                        accumsan sit. Ornare arcu dui vivamus arcu felis. Egestas integer eget aliquet nibh 
                                        praesent. In hac habitasse platea dictumst quisque sagittis purus. Pulvinar elementum 
                                        integer enim neque volutpat ac.
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div style={{border: '1px solid #FAFFFF', marginTop:62, width:1111, height:199, display:'flex', marginBottom:64}}>
                        
                        <div style={{display:'flex', width:'50%', alignItems:'center'}}>
                            <div id='profile2' style={{height: '100%', alignItems:'center',marginLeft:32, marginRight:32}}>
                                <img style={{width: 150, height: 150}} src="https://via.placeholder.com/150x150" />
                            </div>
                            <div style={{display:'flex', flexDirection:'column',marginRight:64}}>
                                <div>김영희</div>
                                <div>2000.01.01</div>
                                <div>email@email.com</div>
                            </div>
                            <div><button id='follow'>Follow</button></div>
                        </div>
                        <div style={{width:'50%'}}><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/558x199" /></div>
                    </div>

                    <div style={{width:1140}}>
                        <h2>작가의 다른 작품</h2>
                        <div>
                            {idx.map((index) => (
                                <div class="img_art"><Link to={'/artworks/'+index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>
                    </div>

                    <div style={{width:1140}}>
                        <h2>연관 다른 작품</h2>
                        <div>
                            {idx.map((index) => (
                                <div class="img_art"><Link to={'/artworks/'+index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default App
