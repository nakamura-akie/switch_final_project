import React from 'react';
import styles from './Footer.module.css';

const Footer = () => {
    return (
        <footer className={styles.footer}>
            <p className={styles.p}>&copy; {new Date().getFullYear()} G6. All rights reserved. Trademark &trade;.</p>
        </footer>
    );
};

export default Footer;