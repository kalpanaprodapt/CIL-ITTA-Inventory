import { expect, test } from '@playwright/test';

test.describe('Optical', () => {
  test('Optical Channel list renders', async ({ page }) => {
    await page.goto('/optical/optical-channels');
    await expect(page.getByRole('heading', { name: 'Optical Channels' }).or(page.getByRole('heading', { name: 'Optical Channeles' }))).toBeVisible();
  });

  test('Optical Channel create form is reachable', async ({ page }) => {
    await page.goto('/optical/optical-channels/new');
    await expect(page.getByRole('form', { name: 'Optical Channel form' })).toBeVisible();
  });

  test('Wdm System list renders', async ({ page }) => {
    await page.goto('/optical/wdm-systems');
    await expect(page.getByRole('heading', { name: 'Wdm Systems' }).or(page.getByRole('heading', { name: 'Wdm Systemes' }))).toBeVisible();
  });

  test('Wdm System create form is reachable', async ({ page }) => {
    await page.goto('/optical/wdm-systems/new');
    await expect(page.getByRole('form', { name: 'Wdm System form' })).toBeVisible();
  });

  test('Otn Link list renders', async ({ page }) => {
    await page.goto('/optical/otn-links');
    await expect(page.getByRole('heading', { name: 'Otn Links' }).or(page.getByRole('heading', { name: 'Otn Linkes' }))).toBeVisible();
  });

  test('Otn Link create form is reachable', async ({ page }) => {
    await page.goto('/optical/otn-links/new');
    await expect(page.getByRole('form', { name: 'Otn Link form' })).toBeVisible();
  });

  test('Amplifier list renders', async ({ page }) => {
    await page.goto('/optical/amplifiers');
    await expect(page.getByRole('heading', { name: 'Amplifiers' }).or(page.getByRole('heading', { name: 'Amplifieres' }))).toBeVisible();
  });

  test('Amplifier create form is reachable', async ({ page }) => {
    await page.goto('/optical/amplifiers/new');
    await expect(page.getByRole('form', { name: 'Amplifier form' })).toBeVisible();
  });

  test('Roadm list renders', async ({ page }) => {
    await page.goto('/optical/roadms');
    await expect(page.getByRole('heading', { name: 'Roadms' }).or(page.getByRole('heading', { name: 'Roadmes' }))).toBeVisible();
  });

  test('Roadm create form is reachable', async ({ page }) => {
    await page.goto('/optical/roadms/new');
    await expect(page.getByRole('form', { name: 'Roadm form' })).toBeVisible();
  });
});
