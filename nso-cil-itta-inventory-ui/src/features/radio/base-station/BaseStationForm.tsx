import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { BASE_STATION_TECHNOLOGY_OPTIONS, BASE_STATION_LIFECYCLE_STAGE_OPTIONS, BaseStationTechnology, BaseStationLifecycleStage } from './types';
import { validateBaseStation, cleanBaseStationValues } from './schema';
import { BASESTATION_DEFAULTS } from './types';
import type { BaseStationFormValues, BaseStationInput } from './types';

interface Props {
  initial?: BaseStationFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: BaseStationInput) => void;
  onCancel?: () => void;
}

export function BaseStationForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<BaseStationFormValues>({ ...BASESTATION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof BaseStationFormValues>(key: K, value: BaseStationFormValues[K]) {
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
    const result = validateBaseStation(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanBaseStationValues(values as Record<string, unknown>) as unknown as BaseStationInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Base Station form">
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
      <FormField label="Site Id" htmlFor="siteId" error={errors.siteId} required={true}>
        <Input
          id="siteId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.siteId ?? ''}
          onChange={(v) => set('siteId', v)}
        />
      </FormField>
      <FormField label="Enb Id" htmlFor="enbId" error={errors.enbId} required={true}>
        <NumberInput
          id="enbId"
          value={values.enbId}
          step={1}
          onChange={(v) => set('enbId', v)}
        />
      </FormField>
      <FormField label="Technology" htmlFor="technology" error={errors.technology} required={false}>
        <Select
          id="technology"
          value={values.technology ?? ''}
          options={BASE_STATION_TECHNOLOGY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('technology', (v || undefined) as BaseStationTechnology | undefined)}
        />
      </FormField>
      <FormField label="Sector Count" htmlFor="sectorCount" error={errors.sectorCount} required={false}>
        <NumberInput
          id="sectorCount"
          value={values.sectorCount}
          step={1}
          onChange={(v) => set('sectorCount', v)}
        />
      </FormField>
      <FormField label="Cell Count" htmlFor="cellCount" error={errors.cellCount} required={false}>
        <NumberInput
          id="cellCount"
          value={values.cellCount}
          step={1}
          onChange={(v) => set('cellCount', v)}
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
          options={BASE_STATION_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as BaseStationLifecycleStage | undefined)}
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
