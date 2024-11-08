import {Link,} from "react-router-dom";


const Navbar = ({items}) => {

    return (
        <div className="navbar">
            <ul className="nav-menu-items">
                {
                    items.map(item => (
                        <li
                            id="link"
                            key={item.key}
                        >
                            <Link id="link" to={item.key} className="linkStyle">
                                <button className="menu-buttons">
                                    <i className="menu-icon">{item.icon}</i>
                                    {item.label}
                                </button>
                            </Link>
                        </li>
                    ))
                }
            </ul>
        </div>
    )
}

export default Navbar;