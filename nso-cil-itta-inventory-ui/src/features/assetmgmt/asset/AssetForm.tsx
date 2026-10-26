import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ASSET_LIFECYCLE_STAGE_OPTIONS, AssetLifecycleStage } from './types';
import { validateAsset, cleanAssetValues } from './schema';
import { ASSET_DEFAULTS } from './types';
import type { AssetFormValues, AssetInput } from './types';

interface Props {
  initial?: AssetFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AssetInput) => void;
  onCancel?: () => void;
}

export function AssetForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AssetFormValues>({ ...ASSET_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AssetFormValues>(key: K, value: AssetFormValues[K]) {
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
    const result = validateAsset(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAssetValues(values as Record<string, unknown>) as unknown as AssetInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Asset form">
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
      <FormField label="Asset Tag" htmlFor="assetTag" error={errors.assetTag} required={true}>
        <Input
          id="assetTag"
          value={values.assetTag ?? ''}
          maxLength={255}
          onChange={(v) => set('assetTag', v)}
        />
      </FormField>
      <FormField label="Asset Class" htmlFor="assetClass" error={errors.assetClass} required={true}>
        <Input
          id="assetClass"
          value={values.assetClass ?? ''}
          maxLength={255}
          onChange={(v) => set('assetClass', v)}
        />
      </FormField>
      <FormField label="Acquired On" htmlFor="acquiredOn" error={errors.acquiredOn} required={false}>
        <DateInput
          id="acquiredOn"
          value={values.acquiredOn ?? ''}
          onChange={(v) => set('acquiredOn', v)}
        />
      </FormField>
      <FormField label="Purchase Price" htmlFor="purchasePrice" error={errors.purchasePrice} required={false}>
        <NumberInput
          id="purchasePrice"
          value={values.purchasePrice}
          step={0.0001}
          onChange={(v) => set('purchasePrice', v)}
        />
      </FormField>
      <FormField label="Current Value" htmlFor="currentValue" error={errors.currentValue} required={false}>
        <NumberInput
          id="currentValue"
          value={values.currentValue}
          step={0.0001}
          onChange={(v) => set('currentValue', v)}
        />
      </FormField>
      <FormField label="Custodian" htmlFor="custodian" error={errors.custodian} required={false}>
        <Input
          id="custodian"
          value={values.custodian ?? ''}
          maxLength={255}
          onChange={(v) => set('custodian', v)}
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
          options={ASSET_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as AssetLifecycleStage | undefined)}
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
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
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
