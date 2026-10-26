import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SERVICE_CATALOG_ITEM_FAMILY_OPTIONS, SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_OPTIONS, ServiceCatalogItemFamily, ServiceCatalogItemLifecycleStage } from './types';
import { validateServiceCatalogItem, cleanServiceCatalogItemValues } from './schema';
import { SERVICECATALOGITEM_DEFAULTS } from './types';
import type { ServiceCatalogItemFormValues, ServiceCatalogItemInput } from './types';

interface Props {
  initial?: ServiceCatalogItemFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ServiceCatalogItemInput) => void;
  onCancel?: () => void;
}

export function ServiceCatalogItemForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ServiceCatalogItemFormValues>({ ...SERVICECATALOGITEM_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ServiceCatalogItemFormValues>(key: K, value: ServiceCatalogItemFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateServiceCatalogItem(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanServiceCatalogItemValues(values as Record<string, unknown>) as unknown as ServiceCatalogItemInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Service Catalog Item form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Item Name" htmlFor="itemName" error={errors.itemName} required={true}>
        <Input
          id="itemName"
          value={values.itemName ?? ''}
          maxLength={255}
          onChange={(v) => set('itemName', v)}
        />
      </FormField>
      <FormField label="Family" htmlFor="family" error={errors.family} required={false}>
        <Select
          id="family"
          value={values.family ?? ''}
          options={SERVICE_CATALOG_ITEM_FAMILY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('family', (v || undefined) as ServiceCatalogItemFamily | undefined)}
        />
      </FormField>
      <FormField label="Base Price" htmlFor="basePrice" error={errors.basePrice} required={false}>
        <NumberInput
          id="basePrice"
          value={values.basePrice}
          step={0.0001}
          onChange={(v) => set('basePrice', v)}
        />
      </FormField>
      <FormField label="Billing Unit" htmlFor="billingUnit" error={errors.billingUnit} required={false}>
        <Input
          id="billingUnit"
          value={values.billingUnit ?? ''}
          maxLength={255}
          onChange={(v) => set('billingUnit', v)}
        />
      </FormField>
      <FormField label="Orderable" htmlFor="orderable" error={errors.orderable} required={false}>
        <Checkbox
          id="orderable"
          checked={Boolean(values.orderable)}
          onChange={(v) => set('orderable', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ServiceCatalogItemLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
