import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SOFTWARE_VERSION_RELEASE_TRAIN_OPTIONS, SOFTWARE_VERSION_CRITICALITY_OPTIONS } from './types';
import type { SoftwareVersionFilter } from './types';

interface Props {
  value: SoftwareVersionFilter;
  onChange: (next: SoftwareVersionFilter) => void;
  onReset: () => void;
}

export function SoftwareVersionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SoftwareVersionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Software Version filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Software Version"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Image Id">
        <Input
          aria-label="Image Id filter"
          placeholder="UUID"
          value={value.imageId ?? ''}
          onChange={(v) => patch({ imageId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Version String">
        <Input
          aria-label="Version String filter"
          value={value.versionString ?? ''}
          onChange={(v) => patch({ versionString: v || undefined })}
        />
      </FilterField>
      <FilterField label="Release Date">
        <DateRange
          ariaLabel="Release Date"
          from={value.releaseDateFrom}
          to={value.releaseDateTo}
          onChange={(from, to) => patch({ releaseDateFrom: from, releaseDateTo: to })}
        />
      </FilterField>
      <FilterField label="End Of Support">
        <DateRange
          ariaLabel="End Of Support"
          from={value.endOfSupportFrom}
          to={value.endOfSupportTo}
          onChange={(from, to) => patch({ endOfSupportFrom: from, endOfSupportTo: to })}
        />
      </FilterField>
      <FilterField label="Release Train">
        <MultiSelect
          ariaLabel="Release Train filter"
          options={SOFTWARE_VERSION_RELEASE_TRAIN_OPTIONS}
          value={value.releaseTrain ?? []}
          onChange={(v) => patch({ releaseTrain: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cve Count">
        <RangeInputs
          ariaLabel="Cve Count"
          min={value.cveCountMin}
          max={value.cveCountMax}
          onChange={(min, max) => patch({ cveCountMin: min, cveCountMax: max })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SOFTWARE_VERSION_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
