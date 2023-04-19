import logo from './logo.svg';
import './App.css';
import NavigationBar from './components/NavigationBar';
import Header from './components/Header';
import SearchCar from './components/SearchCar';
import Footer from './components/Footer';

function App() {
  return (
    <div className="main">
      <Header/>  
      <SearchCar/>
      <Footer/>
    </div>
  );
}

export default App;
