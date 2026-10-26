import { expect, test } from '@playwright/test';

test.describe('Network Device', () => {
  test('Network Device list renders', async ({ page }) => {
    await page.goto('/device/network-devices');
    await expect(page.getByRole('heading', { name: 'Network Devices' }).or(page.getByRole('heading', { name: 'Network Devicees' }))).toBeVisible();
  });

  test('Network Device create form is reachable', async ({ page }) => {
    await page.goto('/device/network-devices/new');
    await expect(page.getByRole('form', { name: 'Network Device form' })).toBeVisible();
  });

  test('Chassis list renders', async ({ page }) => {
    await page.goto('/device/chassises');
    await expect(page.getByRole('heading', { name: 'Chassiss' }).or(page.getByRole('heading', { name: 'Chassises' }))).toBeVisible();
  });

  test('Chassis create form is reachable', async ({ page }) => {
    await page.goto('/device/chassises/new');
    await expect(page.getByRole('form', { name: 'Chassis form' })).toBeVisible();
  });

  test('Line Card list renders', async ({ page }) => {
    await page.goto('/device/line-cards');
    await expect(page.getByRole('heading', { name: 'Line Cards' }).or(page.getByRole('heading', { name: 'Line Cardes' }))).toBeVisible();
  });

  test('Line Card create form is reachable', async ({ page }) => {
    await page.goto('/device/line-cards/new');
    await expect(page.getByRole('form', { name: 'Line Card form' })).toBeVisible();
  });

  test('Power Supply list renders', async ({ page }) => {
    await page.goto('/device/power-supplies');
    await expect(page.getByRole('heading', { name: 'Power Supplys' }).or(page.getByRole('heading', { name: 'Power Supplyes' }))).toBeVisible();
  });

  test('Power Supply create form is reachable', async ({ page }) => {
    await page.goto('/device/power-supplies/new');
    await expect(page.getByRole('form', { name: 'Power Supply form' })).toBeVisible();
  });

  test('Fan Tray list renders', async ({ page }) => {
    await page.goto('/device/fan-traies');
    await expect(page.getByRole('heading', { name: 'Fan Trays' }).or(page.getByRole('heading', { name: 'Fan Trayes' }))).toBeVisible();
  });

  test('Fan Tray create form is reachable', async ({ page }) => {
    await page.goto('/device/fan-traies/new');
    await expect(page.getByRole('form', { name: 'Fan Tray form' })).toBeVisible();
  });
});
