import './assets/css/artist.css';
import { Link } from 'react-router-dom';

function App() {

  const idx = ['1','2','3'];
  const idx2 = ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15'];

  return (
    <>
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px', width: 1140}}>
            <h2>작가 작품 더보기</h2> 
            <div>
                {idx.map((index) => (
                    <div class="img_main"><Link to={index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                ))}

                {idx2.map((index2) => (
                    <div class="img_sub"><Link to={index2}><img src="https://via.placeholder.com/261x261" alt=""/></Link></div>
                ))}   
            </div>
        </div>
    </div>
    </>
  )
}

export default App
