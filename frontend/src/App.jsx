import { createBrowserRouter, Route, Routes } from "react-router-dom";
import Header from './Header.jsx'
import Home from './Home.jsx'
import Artist from './Artist.jsx'
import Artworks from './Artworks.jsx'
import Exhibition from './Exhibition.jsx'
import ExhDetail from './ExhDetail.jsx'
import Mentoring from './Mentoring.jsx'

function App() {

  return (
    <div>
      
      <Routes>
        <Route element={<Header />}>
          <Route path="" element={<Home />} />
          <Route path="artist" element={<Artist />} />
          <Route path="artworks" element={<Artworks />} />
          <Route path="exhibition" element={<Exhibition />} />
          <Route path="exhibition/:exhID" element={<ExhDetail />} />
          <Route path="mentoring" element={<Mentoring />} />
        </Route>
      </Routes>
    </div>
  );
}
export default App;