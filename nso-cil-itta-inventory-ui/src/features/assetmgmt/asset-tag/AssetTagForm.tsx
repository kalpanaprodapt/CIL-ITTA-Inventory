import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ASSET_TAG_TAG_TYPE_OPTIONS, ASSET_TAG_CRITICALITY_OPTIONS, AssetTagTagType, AssetTagCriticality } from './types';
import { validateAssetTag, cleanAssetTagValues } from './schema';
import { ASSETTAG_DEFAULTS } from './types';
import type { AssetTagFormValues, AssetTagInput } from './types';

interface Props {
  initial?: AssetTagFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AssetTagInput) => void;
  onCancel?: () => void;
}

export function AssetTagForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AssetTagFormValues>({ ...ASSETTAG_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AssetTagFormValues>(key: K, value: AssetTagFormValues[K]) {
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
    const result = validateAssetTag(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAssetTagValues(values as Record<string, unknown>) as unknown as AssetTagInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Asset Tag form">
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
      <FormField label="Asset Id" htmlFor="assetId" error={errors.assetId} required={true}>
        <Input
          id="assetId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.assetId ?? ''}
          onChange={(v) => set('assetId', v)}
        />
      </FormField>
      <FormField label="Tag Value" htmlFor="tagValue" error={errors.tagValue} required={true}>
        <Input
          id="tagValue"
          value={values.tagValue ?? ''}
          maxLength={255}
          onChange={(v) => set('tagValue', v)}
        />
      </FormField>
      <FormField label="Tag Type" htmlFor="tagType" error={errors.tagType} required={false}>
        <Select
          id="tagType"
          value={values.tagType ?? ''}
          options={ASSET_TAG_TAG_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('tagType', (v || undefined) as AssetTagTagType | undefined)}
        />
      </FormField>
      <FormField label="Printed On" htmlFor="printedOn" error={errors.printedOn} required={false}>
        <DateInput
          id="printedOn"
          value={values.printedOn ?? ''}
          onChange={(v) => set('printedOn', v)}
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
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={ASSET_TAG_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as AssetTagCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
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
